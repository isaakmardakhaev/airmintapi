package org.latesoft.briefmint.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.logging.log4j.LogManager
import org.latesoft.briefmint.model.JwtData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {
    private val logger = LogManager.getLogger(JwtService::class)

    fun getTokens(username: String): JwtData {
        return JwtData(
            generateJwtToken(username),
            generateRefreshToken(username),
        )
    }

    fun getUsernameFromToken(token: String): String? {
        return runCatching {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
        }.getOrNull()
    }

    fun validateToken(token: String): Boolean {
        return runCatching {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .payload
        }.onFailure {
                e -> logger.error("${e.javaClass.simpleName}", e)
        }.isSuccess
    }

    private fun generateJwtToken(username: String): String {
        val date = java.util.Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
            .subject(username)
            .expiration(date)
            .signWith(getSigningKey())
            .compact()
    }

    private fun generateRefreshToken(username: String): String {
        val date = java.util.Date.from(LocalDateTime.now().plusDays(92).atZone(ZoneId.systemDefault()).toInstant())
        return Jwts.builder()
            .subject(username)
            .expiration(date)
            .signWith(getSigningKey())
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}