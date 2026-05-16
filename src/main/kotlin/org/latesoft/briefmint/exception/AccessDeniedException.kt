package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

open class AccessDeniedException(
    message: String,
    val statusCode: HttpStatus = HttpStatus.FORBIDDEN,
) : RuntimeException(message)