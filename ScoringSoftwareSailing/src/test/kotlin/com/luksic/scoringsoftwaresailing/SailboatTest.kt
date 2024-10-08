package com.luksic.scoringsoftwaresailing

import com.fasterxml.jackson.databind.ObjectMapper
import com.luksic.scoringsoftwaresailing.payload.request.LoginRequest
import com.luksic.scoringsoftwaresailing.payload.request.SailboatRequest
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class SailboatTest {
    @Autowired
    private val mockMvc: MockMvc? = null
    private lateinit var token: String
    @Autowired
    private val objectMapper: ObjectMapper? = null

    @BeforeEach
    fun loginUser(){
        val loginRequest = LoginRequest("testUser4", "testPassword4")
        val loginResponse = objectMapper?.let {
            MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(it.writeValueAsString(loginRequest))
        }?.let {
            mockMvc?.perform(
                it
            )
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andReturn()?.response?.contentAsString
        }

        token = ObjectMapper().readTree(loginResponse)["token"].asText()
    }

    @Test
    @Throws(Exception::class)
    fun testAddSailboatToUser() {
        // Create a sailboat request object
        val sailboatRequest = SailboatRequest()
        sailboatRequest.boatCountry = "USA"
        sailboatRequest.boatNumber = "01"
        sailboatRequest.boatModel = "ModelX"

        // Perform POST request to /users/{id}/sailboats with the sailboat request as JSON
        mockMvc!!.perform(
            MockMvcRequestBuilders.post("/users/{id}/sailboats", "testUser4")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(sailboatRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
    }
}