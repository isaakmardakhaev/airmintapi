package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.Text
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TextRepository : JpaRepository<Text, Long> {
}