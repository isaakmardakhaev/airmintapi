package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.latesoft.briefmint.model.Folder
import org.latesoft.briefmint.model.request.FolderRequest
import org.latesoft.briefmint.service.FolderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/folders")
class FolderController(private val folderService: FolderService) {
	@Operation(summary = "Get my folders")
	@GetMapping
	fun getMyFolders(): ResponseEntity<List<Folder>> =
		ResponseEntity(folderService.getMyFolders(), HttpStatus.OK)

	@Operation(summary = "Create a new folder")
	@PostMapping
	fun createFolder(@Valid @RequestBody request: FolderRequest): ResponseEntity<Folder> =
		ResponseEntity(folderService.addFolder(request.name), HttpStatus.CREATED)

	@Operation(summary = "Rename the folder")
	@PatchMapping("/{folderId}")
	fun renameFolder(
		@PathVariable folderId: Long,
		@Valid @RequestBody request: FolderRequest
	): ResponseEntity<Folder> = ResponseEntity(
		folderService.renameFolder(folderId, request.name),
		HttpStatus.OK
	)

	@Operation(summary = "Delete a folder")
	@DeleteMapping("/{folderId}")
	fun deleteFolder(@PathVariable folderId: Long)
		= folderService.deleteFolder(folderId)
}