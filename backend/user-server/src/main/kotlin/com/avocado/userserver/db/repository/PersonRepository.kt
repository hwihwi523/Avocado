package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PersonRepository: ReactiveCrudRepository<Person, Long> {
}