package org.latesoft.briefmint.model.request

import io.swagger.v3.oas.annotations.media.Schema

data class NoteRequest(
    @Schema(description = "Note name")
    var name: String,
)
