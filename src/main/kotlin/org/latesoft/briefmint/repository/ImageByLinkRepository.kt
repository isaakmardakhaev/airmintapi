package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.ImageByLink
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageByLinkRepository : JpaRepository<ImageByLink, Long> {
}