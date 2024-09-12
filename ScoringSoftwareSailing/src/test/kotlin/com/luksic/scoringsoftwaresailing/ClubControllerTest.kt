package com.luksic.scoringsoftwaresailing

import com.fasterxml.jackson.databind.ObjectMapper
import com.luksic.scoringsoftwaresailing.payload.request.ClubRequest
import com.luksic.scoringsoftwaresailing.payload.request.LoginRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc

class ClubControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null
    private lateinit var token: String
    @Autowired
    private val objectMapper: ObjectMapper? = null
    @BeforeEach
    fun loginUser(){
        val loginRequest = LoginRequest("testUser4", "testPassword4")
        val loginResponse = objectMapper?.let {
            post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(it.writeValueAsString(loginRequest))
        }?.let {
            mockMvc?.perform(
                it
            )
                ?.andExpect(status().isOk)
                ?.andReturn()?.response?.contentAsString
        }

        token = ObjectMapper().readTree(loginResponse)["token"].asText()
    }
    @Test
    @Throws(Exception::class)

    fun createClub() {
                // Create a club request object
        val clubRequest = ClubRequest("testClub4", "testLocation4")
    val expectResponse="Club created successfully"
        // Perform POST request to /clubs with the club request as JSON
        mockMvc!!.perform(
            post("/clubs")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(clubRequest))
        )
            .andExpect(status().isOk())
    }
}