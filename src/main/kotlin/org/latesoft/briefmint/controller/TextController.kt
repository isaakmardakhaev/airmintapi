package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.latesoft.briefmint.model.Text
import org.latesoft.briefmint.model.request.TextRequest
import org.latesoft.briefmint.service.TextService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/text")
@Tag(name = "Text operations")
class TextController(private val textService: TextService) {
	@Operation(summary = "Add text block")
	@PostMapping("/note/{noteId}")
	fun createText(
		@PathVariable noteId: Long,
		@Valid @RequestBody request: TextRequest,
		@RequestParam(required = false) parentId: Long?
	) : ResponseEntity<Text> = ResponseEntity(
		textService.create(
			request.body, noteId, parentId
		),
		HttpStatus.CREATED
	)

	@Operation(summary = "Update text block")
	@PatchMapping("/{id}")
	fun updateText(
		@PathVariable id: Long,
		@Valid @RequestBody request: TextRequest
	) : ResponseEntity<Text> = ResponseEntity(
		textService.update(id, request.body),
		HttpStatus.OK
	)
}