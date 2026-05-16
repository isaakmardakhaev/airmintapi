package org.latesoft.briefmint.service

import org.latesoft.briefmint.model.Role
import org.latesoft.briefmint.model.User
import org.latesoft.briefmint.model.request.SignInRequest
import org.latesoft.briefmint.model.request.SignUpRequest
import org.latesoft.briefmint.model.response.JwtAuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
	private val userService: UserService,
	private val jwtService: JwtService,
	private val passwordEncoder: PasswordEncoder,
	private val authenticationManager: AuthenticationManager
) {
	@Transactional
	fun signUp(request: SignUpRequest): JwtAuthenticationResponse {
		val user = User(
			username = request.name,
			password = passwordEncoder.encode(request.password)
				?: throw IllegalArgumentException("Invalid password"),
			role = Role.ROLE_USER
		)
		userService.create(user)
		return buildJwtResponse(user.username)
	}

	fun signIn(request: SignInRequest): JwtAuthenticationResponse {
		authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				request.name,
				request.password
			)
		)
		val user = userService.getByUsername(request.name)
		return buildJwtResponse(user.username)
	}

	private fun buildJwtResponse(username: String): JwtAuthenticationResponse {
		val tokens = jwtService.getTokens(username)
		return JwtAuthenticationResponse(
			accessToken = tokens.accessToken,
			refreshToken = tokens.refreshToken,
		)
	}
}