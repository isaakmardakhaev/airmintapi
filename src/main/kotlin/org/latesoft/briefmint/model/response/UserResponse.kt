package org.latesoft.briefmint.model.response

import io.swagger.v3.oas.annotations.media.Schema
import org.latesoft.briefmint.model.Role

@Schema(description = "User Info")
data class UserResponse(
    @Schema(description = "User id")
    val id: Long?,
    @Schema(description = "User name")
    val name: String,
    @Schema(description = "User roles")
    val role: Role
)
