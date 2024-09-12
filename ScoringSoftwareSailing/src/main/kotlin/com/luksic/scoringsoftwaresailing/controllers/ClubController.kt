package com.luksic.scoringsoftwaresailing.controllers

import com.luksic.scoringsoftwaresailing.models.Club
import com.luksic.scoringsoftwaresailing.service.ClubService
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
}