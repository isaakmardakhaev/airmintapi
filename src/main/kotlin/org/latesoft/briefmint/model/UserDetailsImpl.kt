package org.latesoft.briefmint.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val user: User,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority>
        = listOf(SimpleGrantedAuthority(user.role.name))

    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getUser(): User = user
}