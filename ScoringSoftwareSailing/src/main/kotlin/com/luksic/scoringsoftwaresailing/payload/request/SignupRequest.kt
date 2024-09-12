package com.luksic.scoringsoftwaresailing.payload.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest(
    @NotBlank
    @Size(min = 3, max = 20)
    val username: String,

    @NotBlank
    @Size(max = 50)
    @Email
    val email: String,

    val roles: List<String>?,

    @NotBlank
    @Size(min = 6, max = 40)
    val password: String
)