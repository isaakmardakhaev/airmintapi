package org.latesoft.briefmint.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Sign In request")
data class SignInRequest(
    @Schema(description = "Username")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @NotBlank(message = "Name cannot be blank")
    val name: String = "",

    @Schema(description = "Password", example = "maTt10236.")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters")
    @NotBlank(message = "Password cannot be blank")
    val password: String = "",
)
