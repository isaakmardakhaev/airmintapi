package org.latesoft.briefmint.model.response

data class ErrorResponse(
    val msg: String,
    val details: List<String> = emptyList()
) {
}