package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

open class InvalidHierarchyException(
    message: String,
    val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(message)