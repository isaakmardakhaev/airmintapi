package org.latesoft.briefmint.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.latesoft.briefmint.service.JwtService
import org.latesoft.briefmint.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: UserService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(request)
        if (token != null
            && jwtService.validateToken(token)
            && SecurityContextHolder.getContext().authentication == null
        ) {
            jwtService.getUsernameFromToken(token)?.let { username ->
                setUserDetailsToSecurityContextHolder(username, request)
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun setUserDetailsToSecurityContextHolder(username: String, request: HttpServletRequest) {
        val user = userService.loadUserByUsername(username)
        val authentication = UsernamePasswordAuthenticationToken(
            user, null, user.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }

        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)
    }
}