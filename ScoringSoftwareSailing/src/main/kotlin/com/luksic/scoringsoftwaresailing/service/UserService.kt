package com.luksic.scoringsoftwaresailing.service

import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getUserById(id: String): User {
        return userRepository.findById(id).get()
    }

    fun updateUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUser(id: String) {
        userRepository.deleteById(id)
    }



}