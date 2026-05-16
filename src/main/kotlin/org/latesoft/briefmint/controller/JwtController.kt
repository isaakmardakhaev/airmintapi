package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.latesoft.briefmint.model.response.JwtAuthenticationResponse
import org.latesoft.briefmint.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/jwt")
@Tag(name = "JWT endpoints")
class JwtController(private val jwtService: JwtService) {
	@Operation(summary = "Refresh JWT tokens")
	@PostMapping("/refresh")
	fun refresh(@RequestHeader("Authorization") header: String): ResponseEntity<JwtAuthenticationResponse> {
		val refreshToken = header.removePrefix("Bearer ")

		if (!jwtService.validateToken(refreshToken))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

		val username = jwtService.getUsernameFromToken(refreshToken)
			?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

		val tokens = jwtService.getTokens(username)

		return ResponseEntity(
			JwtAuthenticationResponse(
				tokens.accessToken,
				tokens.refreshToken
			),
			HttpStatus.OK
		)
	}
}