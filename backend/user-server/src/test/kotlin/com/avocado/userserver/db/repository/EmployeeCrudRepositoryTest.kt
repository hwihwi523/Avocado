package com.avocado.userserver.db.repository

import com.avocado.userserver.config.R2dbcConfig
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration

@WebFluxTest(EmployeeCrudRepository::class)
@ContextConfiguration(classes = [R2dbcConfig::class])
class EmployeeCrudRepositoryTest {

    @Autowired private lateinit var employeeCrudRepository: EmployeeCrudRepository

    @Test
    suspend fun `테스트`() {

//        runBlocking{
//            val cnt = employeeCrudRepository.count()
//            print(employeeCrudRepository.findAll())
//            println(cnt)
//        }
    }

    @Test
    fun `테스트2`() {
//        print(employeeCrudRepository.count())
    }
}