package com.luksic.scoringsoftwaresailing.security.jwt


import com.luksic.scoringsoftwaresailing.security.services.UserDetailsImpl
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {

    private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

    // Generating a secret key
    @Value("\${beholder.app.jwtSecret}")
    private lateinit var secretKey: ByteArray

    // Encoding the secret key in Base64
    private val base64EncodedSecretKey: String by lazy {
        Base64.getEncoder().encodeToString(secretKey)
    }

    @Value("\${beholder.app.jwtExpirationMs}")
    private var jwtExpirationMs: Long = 0

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64EncodedSecretKey))
    }

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl

        return Jwts.builder()
            .subject(userPrincipal.username)
            .issuedAt(Date())
            .expiration(Date(Date().time + jwtExpirationMs))
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }


    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parser().verifyWith(key).build()
            .parseClaimsJws(token).body.subject

    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(authToken)
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }

        return false
    }
}