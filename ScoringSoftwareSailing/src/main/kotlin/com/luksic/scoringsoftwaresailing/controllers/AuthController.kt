package com.luksic.scoringsoftwaresailing.controllers

import com.luksic.scoringsoftwaresailing.models.ERole
import com.luksic.scoringsoftwaresailing.models.Role
import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.payload.request.LoginRequest
import com.luksic.scoringsoftwaresailing.payload.request.SignupRequest
import com.luksic.scoringsoftwaresailing.payload.response.JwtResponse
import com.luksic.scoringsoftwaresailing.payload.response.MessageResponse
import com.luksic.scoringsoftwaresailing.repository.RoleRepository
import com.luksic.scoringsoftwaresailing.repository.UserRepository
import com.luksic.scoringsoftwaresailing.security.jwt.JwtUtils
import com.luksic.scoringsoftwaresailing.security.services.UserDetailsImpl
import org.springframework.web.bind.annotation.*

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder



@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authenticationManager: AuthenticationManager,
                     private val userRepository: UserRepository,
                     private val roleRepository: RoleRepository,
                     private val passwordEncoder: PasswordEncoder,
                     private val jwtUtils: JwtUtils
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities
            .mapNotNull { obj -> obj.authority }
            .toList()

        return ResponseEntity.ok(
            JwtResponse(
                jwt,
                "Bearer",
                userDetails.id.toString(),
                userDetails.username,
                userDetails.email.toString(),
                roles // Pass roles as a list of strings
            )
        )
    }


    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
        val username = signUpRequest.username
        val email = signUpRequest.email

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Username is already taken!"))
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity
                .badRequest()
                .body(MessageResponse("Error: Email is already in use!"))
        }

        // Fetch roles from the database based on role names provided in the request
        val roles: Set<Any> = signUpRequest.roles?.mapNotNull { roleName ->
            when (roleName.lowercase()) {
                "user" -> roleRepository.findByName(ERole.ROLE_USER)
                "mod" -> roleRepository.findByName(ERole.ROLE_MODERATOR)
                "admin" -> roleRepository.findByName(ERole.ROLE_ADMIN)
                "bar_owner" -> roleRepository.findByName(ERole.ROLE_BAR_OWNER)
                else -> null
            }
        }?.toSet() ?: setOf(roleRepository.findByName(ERole.ROLE_USER))

        val filteredRoles = roles.filterIsInstance<Role>().toSet()

        val user = User(username, email, passwordEncoder.encode(/* rawPassword = */ signUpRequest.password ))
        user.roles = filteredRoles
        userRepository.save(user)
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }
}