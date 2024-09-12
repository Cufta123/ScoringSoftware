package com.luksic.scoringsoftwaresailing.service

import com.luksic.scoringsoftwaresailing.models.Club
import com.luksic.scoringsoftwaresailing.repository.ClubRepository
import org.springframework.stereotype.Service

@Service
class ClubService(
    private val clubRepository: ClubRepository
) {
    fun createClub(club: Club) {
        clubRepository.save(club)
    }
    fun getClubs(): List<Club> {
        return clubRepository.findAll()
    }
    fun getClubById(id: String): Club {
        return clubRepository.findById(id).get()
    }
}