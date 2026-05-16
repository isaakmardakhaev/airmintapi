package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.InvalidTypeException
import org.latesoft.briefmint.model.ImageByLink
import org.latesoft.briefmint.repository.ImageByLinkRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageByLinkService(
	private val imageByLinkRepository: ImageByLinkRepository,
	private val nodeService: NodeService,
	private val userService: UserService,
) {
	@Transactional
	fun create(url: String, noteId: Long, parentId: Long?): ImageByLink =
		nodeService.createNode(ImageByLink(url), noteId, parentId)

	@Transactional
	fun update(id: Long, body: String) {
		val user = userService.getCurrentUser()
		val node = nodeService.getNodeByIdAndUser(id, user)
		if (node !is ImageByLink) throw InvalidTypeException("Node must be a Image")
		node.url = body
		imageByLinkRepository.save(node)
	}
}