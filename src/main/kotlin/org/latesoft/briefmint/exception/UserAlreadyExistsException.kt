package org.latesoft.briefmint.exception

import org.springframework.http.HttpStatus

class UserAlreadyExistsException(
	message: String,
	val statusCode: HttpStatus = HttpStatus.CONFLICT
) : RuntimeException(message)