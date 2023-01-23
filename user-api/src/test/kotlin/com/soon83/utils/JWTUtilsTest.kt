package com.soon83.utils

import com.auth0.jwt.interfaces.DecodedJWT
import com.soon83.config.JWTProperties
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JWTUtilsTest {

    private val log = KotlinLogging.logger {}

    @Test
    fun createTokenTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "ds@gmail.com",
            profileUrl = "profile.jpg",
            username = "나패존",
        )

        val jwtProperties = JWTProperties(
            issuer = "system",
            subject = "auth",
            expireTime = 3600,
            secret = "my-secret",
        )

        val token = JWTUtils.createToken(jwtClaim, jwtProperties)
        assertNotNull(token)

        log.info { "# token: $token" }
    }

    @Test
    fun decodeTest() {
        val jwtClaim = JWTClaim(
            userId = 1,
            email = "ds@gmail.com",
            profileUrl = "profile.jpg",
            username = "나패존",
        )

        val jwtProperties = JWTProperties(
            issuer = "system",
            subject = "auth",
            expireTime = 3600,
            secret = "my-secret",
        )

        val token = JWTUtils.createToken(
            claim = jwtClaim,
            properties = jwtProperties,
        )

        val decode: DecodedJWT = JWTUtils.decode(
            token = token,
            secret = jwtProperties.secret,
            issuer = jwtProperties.issuer,
        )

        with(decode) {
            log.info { "# claims: $claims" }

            val userId = claims["userId"]!!.asLong()
            assertEquals(userId, jwtClaim.userId)

            val email = claims["email"]!!.asString()
            assertEquals(email, jwtClaim.email)

            val profileUrl = claims["profileUrl"]!!.asString()
            assertEquals(profileUrl, jwtClaim.profileUrl)

            val username = claims["username"]!!.asString()
            assertEquals(username, jwtClaim.username)
        }
    }
}