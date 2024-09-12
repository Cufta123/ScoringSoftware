package com.luksic.scoringsoftwaresailing.controllers

import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

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
}