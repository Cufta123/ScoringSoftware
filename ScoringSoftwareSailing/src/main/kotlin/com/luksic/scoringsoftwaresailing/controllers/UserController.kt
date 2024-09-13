package com.luksic.scoringsoftwaresailing.controllers

import com.luksic.scoringsoftwaresailing.models.Sailboat
import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.security.jwt.JwtUtils
import com.luksic.scoringsoftwaresailing.service.ClubService
import com.luksic.scoringsoftwaresailing.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val jwtUtils: JwtUtils) {

    @PostMapping
    fun  createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }

    @GetMapping
    fun getUsers(): List<User> {
        return userService.getUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): User {
        return userService.getUserById(id)
    }

    @PutMapping
    fun updateUser(@RequestBody user: User): User {
        return userService.updateUser(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) {
        userService.deleteUser(id)
    }

    @PostMapping("/{username}/sailboats")
    fun addSailboatToUser(@PathVariable username: String, @RequestBody sailboat: Sailboat) {
        userService.addSailboatToUser(username, sailboat)
    }


    @PostMapping("/user-information")
    fun addAdditionalInformationToUser(
        @RequestHeader(HttpHeaders.AUTHORIZATION) header: String,
        @RequestBody additionalInfo: User
    ): ResponseEntity<User> {
        val username = jwtUtils.getUserNameFromJwtToken(header.substring(7))
        // Fetch the existing user
        val existingUser = userService.getUserByUsername(username)
        // Update the fields you want to change
        existingUser.firstName = additionalInfo.firstName ?: existingUser.firstName
        existingUser.lastName = additionalInfo.lastName ?: existingUser.lastName
        existingUser.dateOfBirth = additionalInfo.dateOfBirth ?: existingUser.dateOfBirth

        val updatedUser = userService.updateUser(existingUser)
        return ResponseEntity.ok(updatedUser)
    }

}