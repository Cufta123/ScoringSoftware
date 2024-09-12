package com.luksic.scoringsoftwaresailing.repository


import com.luksic.scoringsoftwaresailing.models.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String): Optional<User>

    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}