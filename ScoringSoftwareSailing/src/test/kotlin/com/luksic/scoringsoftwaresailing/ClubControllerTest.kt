package com.luksic.scoringsoftwaresailing

import com.fasterxml.jackson.databind.ObjectMapper
import com.luksic.scoringsoftwaresailing.models.User
import com.luksic.scoringsoftwaresailing.payload.request.AddUserRequest
import com.luksic.scoringsoftwaresailing.payload.request.ClubRequest
import com.luksic.scoringsoftwaresailing.payload.request.LoginRequest
import com.luksic.scoringsoftwaresailing.repository.UserRepository
import com.luksic.scoringsoftwaresailing.security.jwt.JwtUtils
import com.luksic.scoringsoftwaresailing.security.services.UserDetailsImpl
import com.luksic.scoringsoftwaresailing.service.UserService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc

class ClubControllerTest{
    @Autowired
    private val mockMvc: MockMvc? = null
    private lateinit var token: String

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private lateinit var jwtUtils: JwtUtils
    @BeforeEach
    fun loginUser(){
        val loginRequest = LoginRequest("testUser69", "testPassword69")
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
        val clubRequest = ClubRequest("testClub2", "testLocation2")
        // Perform POST request to /clubs with the club request as JSON
        mockMvc!!.perform(
            post("/clubs")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(clubRequest))
        )
            .andExpect(status().isOk())
    }

    @Test
    fun addUserToClub(){
        val clubName = "testClub1"
        val addUserRequest = AddUserRequest("testUser1")

        mockMvc!!.perform(
            post("/clubs/$clubName/addUser")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(addUserRequest))
        )
            .andExpect(status().isOk())
    }
    @Test
    @Throws(Exception::class)
    fun testAddAdditionalInformationToUser() {
        // Fetch the existing user
        val existingUser = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token)).get()
        println("Existing user: $existingUser")
        // Update the fields you want to change
        existingUser.firstName = "Bob"
        existingUser.lastName = "marlin"
        existingUser.dateOfBirth = LocalDate.of(1999, 1, 1)
        existingUser.club = "testClub1"

        // Perform POST request to /users/user-information with the updated user object as JSON
        mockMvc!!.perform(
            post("/users/user-information")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper!!.writeValueAsString(existingUser))
        )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Bob"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("marlin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").exists())
    }
    @Test
    fun extractUsernameFromJwtToken() {
        // Create a JWT token for a specific user
        // Extract the username from the JWT token
        val username = jwtUtils.getUserNameFromJwtToken(token)

        // Display the username in the console
        println("Extracted username: $username")
    }
}