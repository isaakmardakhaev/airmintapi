package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.InvalidHierarchyException
import org.latesoft.briefmint.exception.ResourceNotFoundException
import org.latesoft.briefmint.model.Node
import org.latesoft.briefmint.model.User
import org.latesoft.briefmint.repository.NodeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class NodeService(
	private val nodeRepository: NodeRepository,
	private val noteService: NoteService,
	private val userService: UserService
) {
	fun getNodeById(id: Long): Node? = nodeRepository.findById(id).getOrNull()

	fun getNodeByIdAndUser(id: Long, user: User): Node {
		val node = getNodeById(id)
			?: throw ResourceNotFoundException("Node with id $id not found")

		val noteId = node.note?.id
			?: throw ResourceNotFoundException("Associated note with node not found")

		noteService.getNoteByIdAndUser(noteId, user)
		return node
	}

	@Transactional
	fun deleteNode(id: Long) {
		val currentUser = userService.getCurrentUser()
		val node = getNodeByIdAndUser(id, currentUser)
		node.parent?.removeChild(node)
		node.note?.removeNode(node)
		nodeRepository.delete(node)
	}

	@Transactional
	fun <T : Node> createNode(node: T, noteId: Long, parentId: Long?): T {
		val currentUser = userService.getCurrentUser()
		val note = noteService.getNoteByIdAndUser(noteId, currentUser)
		val parent = if(parentId != null) {
			val parentNode = getNodeByIdAndUser(parentId, currentUser)
			if (parentNode.note?.id != note.id)
				throw InvalidHierarchyException("Parent node must be in the same note")
			parentNode
		} else null

		note.addNode(node)
		parent?.addChild(node)
		@Suppress("UNCHECKED_CAST")

		return nodeRepository.save(node) as T
	}
}