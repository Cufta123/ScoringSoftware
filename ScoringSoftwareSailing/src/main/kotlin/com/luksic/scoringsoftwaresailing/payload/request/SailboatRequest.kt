package com.luksic.scoringsoftwaresailing.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class SailboatRequest {
    @NotBlank
    @Size(min = 3, max = 3)
    lateinit var boatCountry: String
    @NotBlank
    @Size(min = 2, max = 2)
    lateinit var boatNumber: String
    @NotBlank
    @Size(min = 3, max = 20)
    lateinit var boatModel: String
}