package com.luksic.scoringsoftwaresailing.payload.request

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank
    val username: String,

    @NotBlank
    val password: String
)