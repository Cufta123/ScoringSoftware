package com.luksic.scoringsoftwaresailing.service


import com.luksic.scoringsoftwaresailing.models.Club
import com.luksic.scoringsoftwaresailing.repository.ClubRepository
import com.luksic.scoringsoftwaresailing.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ClubService(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,

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


    fun addUserToClub(clubName: String, username: String) {
        val club = clubRepository.findByNameOfClub(clubName)
        val userOptional = userRepository.findByUsername(username)

        if (userOptional.isPresent) {
            val user = userOptional.get()

            // Check if the user is already in a different club
            if (user.club != null && user.club != clubName) {
                val oldClub = clubRepository.findByNameOfClub(user.club!!)
                oldClub.listOfUsersInClub.remove(username)
                clubRepository.save(oldClub)
            }

            // Check if the user is already in the club
            if (!club.listOfUsersInClub.contains(username)) {
                club.listOfUsersInClub.add(username)
                user.club = clubName
                clubRepository.save(club)
                userRepository.save(user)
            }
        } else {
            // Handle the case where the user is not found
            throw IllegalArgumentException("User $username not found")
        }
    }
}