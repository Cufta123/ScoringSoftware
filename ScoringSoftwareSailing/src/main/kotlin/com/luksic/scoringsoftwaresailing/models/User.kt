package com.luksic.scoringsoftwaresailing.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "users")
class User(
    @field:NotBlank
    var username: String?,
    @field:NotBlank
    @field:Size(max = 50) @field:Email
    var email: String?,
    @field:NotBlank
    @field:Size(max = 120) var password: String?,
) {
    @Id
    lateinit var id: String
    lateinit var nameOfUser: String
    var club: Club? = null
    var listOfSailboats: MutableList<Sailboat> = mutableListOf()
    @DBRef
    var roles: Set<Role> = HashSet()
}