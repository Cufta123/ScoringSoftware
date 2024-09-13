package com.luksic.scoringsoftwaresailing

import com.fasterxml.jackson.databind.ObjectMapper
import com.luksic.scoringsoftwaresailing.payload.request.LoginRequest
import com.luksic.scoringsoftwaresailing.payload.request.SailboatRequest
import com.luksic.scoringsoftwaresailing.payload.request.SignupRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Test
    @Throws(Exception::class)
    fun testAuthenticateUser() {
        // Create a login request object
        val loginRequest = LoginRequest("testUser4", "testPassword4")

        // Perform POST request to /api/auth/signin with the login request as JSON
        mockMvc!!.perform(
            post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(loginRequest))
        )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Bearer"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.roles").isArray())
    }


    @Test
    @Throws(Exception::class)
    fun testRegisterUser() {
        // Create a signup request object
        val signupRequest = SignupRequest(
            "testUser69",
            "newuser69@example.com",
            listOf("mod", "user"),
            "testPassword69"
        )
        val expectedResponse = "User registered successfully!"
        // Perform POST request to /api/auth/signup with the signup request as JSON
        val responseBody = mockMvc?.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper!!.writeValueAsString(signupRequest)))            ?.andExpect(status().isOk)
            ?.andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.containsString(expectedResponse)))
            ?.andReturn()
            ?.response
            ?.contentAsString

        MatcherAssert.assertThat(responseBody, CoreMatchers.containsString(expectedResponse))
    }


}