package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.InvalidHierarchyException
import org.latesoft.briefmint.exception.ResourceNotFoundException
import org.latesoft.briefmint.model.Note
import org.latesoft.briefmint.model.User
import org.latesoft.briefmint.repository.NoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class NoteService(
	private val noteRepository: NoteRepository,
	private val folderService: FolderService,
	private val userService: UserService
) {
	fun getNoteById(id: Long): Note? = noteRepository.findById(id).getOrNull()

	fun getNoteByIdAndUser(id: Long, user: User): Note {
		val note = getNoteById(id)
			?: throw ResourceNotFoundException("Note with id $id not found")

		val folderId = note.folder?.id
			?: throw ResourceNotFoundException("Note has not associated valid folder")

		folderService.getFolderByIdAndUser(folderId, user)

		return note
	}

	fun getMyNotes(folderId: Long): List<Note> {
		val currentUser = userService.getCurrentUser()
		folderService.getFolderByIdAndUser(folderId, currentUser)
		return noteRepository.findNotesByFolderIdAndParentIsNull(folderId)
	}

	@Transactional
	fun createNote(name: String, folderId: Long, parentId: Long?): Note {
		val currentUser = userService.getCurrentUser()
		val folder = folderService.getFolderByIdAndUser(folderId, currentUser)

		val parent = if (parentId != null) {
			val parentNote = getNoteByIdAndUser(parentId, currentUser)
			if (parentNote.folder?.id != folder.id)
				throw InvalidHierarchyException("Parent note must be in the same folder")
			parentNote
		} else null

		val res = Note(
			name = name,
			folder = folder,
			parent = parent,
		)

		folder.addNote(res)
		parent?.addChild(res)

		return noteRepository.save(res)
	}

	@Transactional
	fun renameNote(id: Long, name: String): Note {
		val currentUser = userService.getCurrentUser()
		val note = getNoteByIdAndUser(id, currentUser)
		note.name = name
		return noteRepository.save(note)
	}

	@Transactional
	fun deleteNote(id: Long) {
		val currentUser = userService.getCurrentUser()
		val note = getNoteByIdAndUser(id, currentUser)
		val folder = note.folder ?: throw ResourceNotFoundException("Associated folder not found")
		note.parent?.removeChild(note)
		folder.removeNote(note)
		noteRepository.delete(note)
	}
}