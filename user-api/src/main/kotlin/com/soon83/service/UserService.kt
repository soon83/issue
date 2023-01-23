package com.soon83.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.soon83.config.JWTProperties
import com.soon83.domain.entity.User
import com.soon83.domain.repository.UserRepository
import com.soon83.exception.InvalidJwtTokenException
import com.soon83.exception.PasswordNotMatchedException
import com.soon83.exception.UserExistsException
import com.soon83.exception.UserNotFoundException
import com.soon83.model.SignInRequest
import com.soon83.model.SignInResponse
import com.soon83.model.SignUpRequest
import com.soon83.utils.BCryptUtils
import com.soon83.utils.JWTClaim
import com.soon83.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )
            userRepository.save(user)
        }
    }

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()
        return with(user) {
            val verified = BCryptUtils.verify(signInRequest.password, password)
            if (!verified) throw PasswordNotMatchedException()

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username,
            )

            val token = JWTUtils.createToken(
                claim = jwtClaim,
                properties = jwtProperties,
            )

            cacheManager.awaitPut(
                key = token,
                value = this,
                ttl = CACHE_TTL,
            )

            SignInResponse(
                email = email,
                username = username,
                token = token,
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getUserByToken(token: String): User {
        val cachedUser = cacheManager.awaitGetOrPut(
            key = token,
            ttl = CACHE_TTL,
        ) {
            val decodedJWT: DecodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)

            val userId: Long = decodedJWT.claims["userId"]?.asLong() ?: throw InvalidJwtTokenException()

            get(userId)
        }
        return cachedUser
    }

    suspend fun get(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }

    suspend fun edit(token: String, username: String, profileUrl: String?): User {
        val user = getUserByToken(token)
        val newUser = user.copy(
            username = username,
            profileUrl = profileUrl ?: user.profileUrl,
        )
        return userRepository.save(newUser).also {
            cacheManager.awaitPut(
                key = token,
                value = it,
                ttl = CACHE_TTL,
            )
        }
    }
}