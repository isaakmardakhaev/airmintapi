package org.latesoft.briefmint.service

import org.latesoft.briefmint.exception.UnauthorizedException
import org.latesoft.briefmint.exception.UserAlreadyExistsException
import org.latesoft.briefmint.model.User
import org.latesoft.briefmint.model.UserDetailsImpl
import org.latesoft.briefmint.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {
	override fun loadUserByUsername(username: String): UserDetails =
		UserDetailsImpl(getByUsername(username))

	private fun assertUsernameAvailable(username: String) {
		if (userRepository.existsByUsername(username))
			throw UserAlreadyExistsException("User $username already exists")
	}

	@Transactional
	fun create(user: User): User {
		assertUsernameAvailable(user.username)
		return userRepository.save(user)
	}

	fun getByUsername(username: String): User =
 		userRepository.findByUsername(username)
			?: throw UsernameNotFoundException("User $username not found")

	fun getCurrentUser(): User {
		val username = SecurityContextHolder.getContext().authentication?.name
			?: throw UnauthorizedException("Unauthorized user")
		return userRepository.findByUsername(username)
			?: throw UnauthorizedException("User $username no longer exists")
	}
}