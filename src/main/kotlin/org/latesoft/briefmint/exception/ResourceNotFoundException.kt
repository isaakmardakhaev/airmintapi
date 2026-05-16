package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

open class ResourceNotFoundException(
    message: String,
    val statusCode: HttpStatus = HttpStatus.NOT_FOUND
) : RuntimeException(message)