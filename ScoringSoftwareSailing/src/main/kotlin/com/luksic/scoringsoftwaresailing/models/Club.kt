package com.luksic.scoringsoftwaresailing.models

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clubs")

class Club {
    @Id
    lateinit var id: String
    lateinit var nameOfClub: String
    lateinit var countryOfOrigin: String
    var listOfUsersInClub: MutableList<String> = mutableListOf()
}