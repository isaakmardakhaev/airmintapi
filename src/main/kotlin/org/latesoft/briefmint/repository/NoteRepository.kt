package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    fun findNotesByFolderIdAndParentIsNull(folderId: Long): List<Note>
}