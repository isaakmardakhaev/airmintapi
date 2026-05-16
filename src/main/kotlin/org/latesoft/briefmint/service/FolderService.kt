package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.AccessDeniedException
import org.latesoft.briefmint.exception.ResourceNotFoundException
import org.latesoft.briefmint.model.Folder
import org.latesoft.briefmint.model.User
import org.latesoft.briefmint.repository.FolderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class FolderService(
	private val folderRepository: FolderRepository,
	private val userService: UserService
) {
	fun getMyFolders(): List<Folder> {
		val currentUser = userService.getCurrentUser()
		return folderRepository.findFoldersByUser(currentUser)
	}

	fun getFolderById(id: Long): Folder? = folderRepository.findById(id).getOrNull()

	fun getFolderByIdAndUser(id: Long, user: User): Folder {
		val folder = getFolderById(id)
			?: throw ResourceNotFoundException("Folder with id $id not found")
		if (folder.user?.id != user.id)
			throw AccessDeniedException("Access for folder with id $id denied")
		return folder
	}

	@Transactional
	fun addFolder(name: String): Folder {
		val currentUser = userService.getCurrentUser()
		return folderRepository.save(Folder(
			name = name,
			user = currentUser
		))
	}

	@Transactional
	fun renameFolder(id: Long, name: String): Folder {
		val currentUser = userService.getCurrentUser()
		val folder = getFolderByIdAndUser(id, currentUser)
		folder.name = name
		return folderRepository.save(folder)
	}

	@Transactional
	fun deleteFolder(id: Long) {
		val currentUser = userService.getCurrentUser()
		val folder = getFolderByIdAndUser(id, currentUser)
		folderRepository.delete(folder)
	}
}