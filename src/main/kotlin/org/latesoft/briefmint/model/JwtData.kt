package org.latesoft.briefmint.model

data class JwtData(
    val accessToken: String,
    val refreshToken: String
)