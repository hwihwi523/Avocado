package com.avocado.userserver.db.repository

import com.avocado.userserver.config.R2dbcConfig
import com.avocado.userserver.db.entity.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [R2dbcConfig::class])
class PersonRepositoryTest(
    @Autowired private val personRepository: PersonRepository,
    @Autowired private val databaseClient: DatabaseClient
) {

    @BeforeEach
    fun beforeEach() {
        databaseClient
            .sql("delete from person")
            .fetch()
            .rowsUpdated()
            .block()
    }

    @Test
    private fun `연결 확인`() {
//        databaseClient.sql("INSERT INTO person VALUES(1, 'HYEJIN', 'LIM')")
//            .

        databaseClient.sql("SELECT * FROM person")
            .fetch()
            .all()

    }


    private fun generatePerson(id: Long? = null): Person {
        return Person(
            id = id,
            firstname = UUID.randomUUID().toString(),
            lastname = UUID.randomUUID().toString()
        )
    }

    @Test
    fun `test save`() {
        val person = generatePerson()
        println(person)
        personRepository.save(person)
            .`as`{ StepVerifier.create(it)}
            .expectNextCount(1)
            .verifyComplete()
    }

}