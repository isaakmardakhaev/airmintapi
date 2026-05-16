package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.latesoft.briefmint.model.request.SignInRequest
import org.latesoft.briefmint.model.request.SignUpRequest
import org.latesoft.briefmint.model.response.JwtAuthenticationResponse
import org.latesoft.briefmint.model.response.UserResponse
import org.latesoft.briefmint.service.AuthService
import org.latesoft.briefmint.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/auth"])
@Tag(name = "Auth")
class AuthController(
	private val authService: AuthService,
	private val userService: UserService
) {
	@Operation(summary = "Sign up")
	@PostMapping("/sign-up")
	fun signUp(@Valid @RequestBody request: SignUpRequest): ResponseEntity<JwtAuthenticationResponse> =
		ResponseEntity(authService.signUp(request), HttpStatus.OK)

	@Operation(summary = "Sign in")
	@PostMapping("/sign-in")
	fun signIn(@Valid @RequestBody request: SignInRequest): ResponseEntity<JwtAuthenticationResponse> =
		ResponseEntity(authService.signIn(request), HttpStatus.OK)

	@Operation(summary = "Get myself")
	@GetMapping("/myself")
	fun getMySelf(): ResponseEntity<UserResponse> {
		val currentUser = userService.getCurrentUser()
		return ResponseEntity(
			UserResponse(
				currentUser.id,
				currentUser.username,
				currentUser.role
			),
			HttpStatus.OK
		)
	}
}