package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

class InvalidTypeException(
	message: String,
	val statusCode: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(message)