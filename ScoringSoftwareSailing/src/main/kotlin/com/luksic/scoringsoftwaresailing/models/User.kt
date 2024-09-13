package com.luksic.scoringsoftwaresailing.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jdk.jfr.Category
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate



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
    var firstName: String= ""
    var lastName: String=  ""
    var category: UserCategory = UserCategory.CADET
    var dateOfBirth: LocalDate = LocalDate.now()
    var club: String? = null
    var listOfSailboats: MutableList<Sailboat> = mutableListOf()
    @DBRef
    var roles: Set<Role> = HashSet()

    override fun toString(): String {
        return "User(username ='$username'firstName='$firstName', lastName='$lastName', dateOfBirth=$dateOfBirth, club='$club')"
    }
}