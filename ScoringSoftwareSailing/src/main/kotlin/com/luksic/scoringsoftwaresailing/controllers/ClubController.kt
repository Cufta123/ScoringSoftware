package com.luksic.scoringsoftwaresailing.controllers

import com.luksic.scoringsoftwaresailing.models.Club
import com.luksic.scoringsoftwaresailing.payload.request.AddUserRequest
import com.luksic.scoringsoftwaresailing.service.ClubService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clubs")
class ClubController (
    private val clubService: ClubService
){
    @PostMapping
    fun createClub(@RequestBody club: Club){
        clubService.createClub(club)
    }

    @GetMapping("/all")
    fun getClubs(): List<Club> {
        return clubService.getClubs()
    }
    @GetMapping("/{id}")
    fun getClubById(@PathVariable id: String): Club {
        return clubService.getClubById(id)
    }

    @PostMapping("/{clubName}/addUser")
    fun addUserToClub(@PathVariable clubName: String, @RequestBody addUserRequest: AddUserRequest): ResponseEntity<Void> {
        clubService.addUserToClub(clubName, addUserRequest.username)
        return ResponseEntity.ok().build()
    }
}