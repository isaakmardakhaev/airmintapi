package org.latesoft.briefmint.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Text block request")
data class TextRequest(
    @Schema(description = "Text block")
    @NotBlank(message = "Text block can not be blank")
    val body: String,
)
