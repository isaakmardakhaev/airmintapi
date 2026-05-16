package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.Folder
import org.latesoft.briefmint.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FolderRepository : JpaRepository<Folder, Long> {
    fun findFoldersByUser(user: User): MutableList<Folder>
}