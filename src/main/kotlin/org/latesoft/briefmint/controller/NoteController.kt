package org.latesoft.briefmint.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.latesoft.briefmint.model.Note
import org.latesoft.briefmint.model.request.NoteRequest
import org.latesoft.briefmint.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/notes"])
@Tag(name = "Notes")
class NoteController(private val noteService: NoteService) {
	@Operation(summary = "Get my notes")
	@GetMapping("/folder/{folderId}")
	fun getMyNotesByFolder(@PathVariable folderId: Long): ResponseEntity<List<Note>> =
		ResponseEntity(noteService.getMyNotes(folderId), HttpStatus.OK)

	@Operation(summary = "Create a new note")
	@PostMapping("/folder/{folderId}")
	fun createNote(
		@PathVariable folderId: Long,
		@Valid @RequestBody request: NoteRequest,
		@RequestParam(required = false) parentId: Long?
	): ResponseEntity<Note> = ResponseEntity(
		noteService.createNote(
			name = request.name,
			folderId = folderId,
			parentId = parentId
		),
		HttpStatus.CREATED
	)

	@Operation(summary = "Update a note")
	@PatchMapping("/{noteId}")
	fun renameNote(
		@PathVariable noteId: Long,
		@Valid @RequestBody request: NoteRequest,
	): ResponseEntity<Note> = ResponseEntity(
		noteService.renameNote(noteId, request.name),
		HttpStatus.OK
	)

	@Operation(summary = "Delete a note")
	@DeleteMapping("/{noteId}")
	fun deleteNote(@PathVariable noteId: Long): ResponseEntity<Unit> {
		noteService.deleteNote(noteId)
		return ResponseEntity.noContent().build()
	}
}