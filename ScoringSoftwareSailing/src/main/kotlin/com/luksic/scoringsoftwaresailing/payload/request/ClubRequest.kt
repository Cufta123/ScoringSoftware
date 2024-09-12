package com.luksic.scoringsoftwaresailing.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ClubRequest (
    @NotBlank
    @Size(min = 3, max = 20)
    val nameOfTheClub: String,
    @NotBlank
    @Size(min = 3, max = 20)
    val countryOfOrigin: String
)