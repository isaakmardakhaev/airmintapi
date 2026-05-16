package org.latesoft.briefmint.model.request

import io.swagger.v3.oas.annotations.media.Schema

data class FolderRequest(
    @Schema(description = "Folder name")
    var name: String,
)
