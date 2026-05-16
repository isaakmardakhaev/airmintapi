package org.latesoft.briefmint.model.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Image by URL request")
data class ImageByLinkRequest(
    @Schema(description = "URL of the image")
    @NotBlank(message = "URL can not be blank")
    val url: String,
)
