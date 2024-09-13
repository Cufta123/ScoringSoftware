package com.luksic.scoringsoftwaresailing.repository

import com.luksic.scoringsoftwaresailing.models.Club
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ClubRepository : MongoRepository<Club, String>
{
    fun findByNameOfClub(nameOfClub: String): Club
    fun findByCountryOfOrigin(countryOfOrigin: String): Club
    fun deleteByNameOfClub(nameOfClub: String)
    fun deleteByCountryOfOrigin(countryOfOrigin: String)
}