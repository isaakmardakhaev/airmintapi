package org.latesoft.briefmint.model.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response with JWT tokens")
data class JwtAuthenticationResponse(
    @Schema(description = "Access token")
    val accessToken: String,
    @Schema(description = "Refresh token")
    val refreshToken: String,
)