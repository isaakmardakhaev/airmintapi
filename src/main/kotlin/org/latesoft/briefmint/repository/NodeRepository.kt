package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.Node
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NodeRepository : JpaRepository<Node, Long> {
}