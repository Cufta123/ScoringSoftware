package com.luksic.scoringsoftwaresailing.service

import com.luksic.scoringsoftwaresailing.models.Sailboat
import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.models.UserCategory
import com.luksic.scoringsoftwaresailing.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

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
        calculateUserCategory(user)
        return userRepository.save(user)
    }
    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username).get()
    }
    fun deleteUser(id: String) {
        userRepository.deleteById(id)
    }

    fun addSailboatToUser(username: String, sailboat: Sailboat) {
        val user = userRepository.findByUsername(username).get()
        user.listOfSailboats.add(sailboat)
        userRepository.save(user)
    }
    fun calculateUserCategory(user: User) {
        val currentYear = LocalDate.now().year
        val age = currentYear - user.dateOfBirth.year
        user.category = when {
            age <= 15 -> UserCategory.CADET
            age in 16..18 -> UserCategory.JUNIOR
            age in 19..35 -> UserCategory.SENIOR
            age in 36..50 -> UserCategory.MASTER
            else -> UserCategory.GRAND_MASTER
        }
    }

}