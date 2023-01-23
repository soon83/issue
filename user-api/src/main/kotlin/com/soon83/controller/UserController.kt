package com.soon83.controller

import com.soon83.model.*
import com.soon83.service.UserService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KotlinLogging
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    private val log = KotlinLogging.logger {}

    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        log.debug { "# signUp # request: $request" }
        userService.signUp(request)
    }

    @PostMapping("signin")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        log.debug { "# signIn # request: $request" }
        return userService.signIn(request)
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) {
        log.debug { "# logout # token: $token" }
        userService.logout(token)
    }

    @GetMapping("/me")
    suspend fun get(@AuthToken token: String): MeResponse {
        log.debug { "# get # token: $token" }
        val user = userService.getUserByToken(token)
        return MeResponse(user)
    }

    @GetMapping("/{userId}/username")
    suspend fun getReporter(@PathVariable userId: Long) =
        mapOf("reporter" to userService.get(userId).username)

    @PostMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun edit(
        @PathVariable id: Long,
        @ModelAttribute request: UserEditRequest,
        @AuthToken token: String,
        @RequestPart("profileUrl") filePart: FilePart,
    ) {
        val orgFilename = filePart.filename()
        var filename: String? = null
        if (orgFilename.isNotBlank()) {
            val ext = orgFilename.substring(orgFilename.lastIndexOf(".") + 1)
            filename = "$id.$ext"

            val file = File(ClassPathResource("/images").file, filename)
            filePart.transferTo(file).awaitSingleOrNull()
        }
        userService.edit(token, request.username, filename)
    }
}