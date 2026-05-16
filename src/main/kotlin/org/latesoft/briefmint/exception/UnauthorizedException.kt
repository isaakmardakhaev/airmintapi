package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

open class UnauthorizedException(
    message: String,
    val statusCode: HttpStatus = HttpStatus.UNAUTHORIZED
) : RuntimeException(message)