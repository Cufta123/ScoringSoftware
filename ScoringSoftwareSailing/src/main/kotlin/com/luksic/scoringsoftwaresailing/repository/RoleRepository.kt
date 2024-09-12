package com.luksic.scoringsoftwaresailing.repository

import com.luksic.scoringsoftwaresailing.models.ERole
import com.luksic.scoringsoftwaresailing.models.Role
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository : MongoRepository<Role, String>{
    fun findByName(name: ERole): Role
}