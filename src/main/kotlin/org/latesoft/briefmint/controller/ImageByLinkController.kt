package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.latesoft.briefmint.model.ImageByLink
import org.latesoft.briefmint.model.request.ImageByLinkRequest
import org.latesoft.briefmint.service.ImageByLinkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/image")
@Tag(name = "Images By Link operations")
class ImageByLinkController(private val imageByLinkService: ImageByLinkService) {
	@Operation(summary = "Add image by URL")
	@PostMapping("/note/{noteId}")
	fun createImage(
		@PathVariable noteId: Long,
		@Valid @RequestBody request: ImageByLinkRequest,
		@RequestParam(required = false) parentId: Long?
	) : ResponseEntity<ImageByLink> = ResponseEntity(
		imageByLinkService.create(
			request.url, noteId, parentId
		),
		HttpStatus.CREATED
	)
}