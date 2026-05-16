package org.latesoft.briefmint.config

import org.latesoft.briefmint.exception.InvalidHierarchyException
import org.latesoft.briefmint.exception.InvalidTypeException
import org.latesoft.briefmint.exception.ResourceNotFoundException
import org.latesoft.briefmint.exception.UnauthorizedException
import org.latesoft.briefmint.exception.AccessDeniedException
import org.latesoft.briefmint.exception.UserAlreadyExistsException
import org.latesoft.briefmint.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map {
            it.defaultMessage ?: "Validation error"
        }
        return ResponseEntity(
            ErrorResponse("Request data error", errors),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Internal error occurred"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("Invalid username or password"),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Access denied"),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(InvalidHierarchyException::class)
    fun handleInvalidHierarchyException(ex: InvalidHierarchyException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Invalid hierarchy"),
            ex.statusCode
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Resource not found"),
            ex.statusCode
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Unauthorized"),
            ex.statusCode

        )
    }

    @ExceptionHandler(InvalidTypeException::class)
    fun handleInvalidTypeException(ex: InvalidTypeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "Invalid type"),
            ex.statusCode
        )
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(ex.message ?: "User already exists"),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("Some error occured", listOf(ex.localizedMessage)),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}