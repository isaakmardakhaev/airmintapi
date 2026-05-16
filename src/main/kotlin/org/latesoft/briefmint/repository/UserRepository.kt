package org.latesoft.briefmint.repository

import org.latesoft.briefmint.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String?): User?
    fun existsByUsername(username: String): Boolean
}