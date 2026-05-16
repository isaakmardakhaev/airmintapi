package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.InvalidTypeException
import org.latesoft.briefmint.model.Text
import org.latesoft.briefmint.repository.TextRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TextService(
	private val textRepository: TextRepository,
	private val nodeService: NodeService,
	private val userService: UserService,
) {
	@Transactional
	fun create(body: String, noteId: Long, parentId: Long?): Text =
		nodeService.createNode(Text(body), noteId, parentId)

	@Transactional
	fun update(id: Long, body: String): Text {
		val user = userService.getCurrentUser()
		val node = nodeService.getNodeByIdAndUser(id, user)
		if (node !is Text) throw InvalidTypeException("Node must be a Text")
		node.body = body
		return textRepository.save(node) as Text
	}
}