package com.avocado.userserver.db.repository

import com.avocado.userserver.config.R2dbcConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration

@WebFluxTest(ProviderRepository::class)
@ContextConfiguration(classes = [R2dbcConfig::class])
class ProviderRepositoryTest {

    @Autowired private lateinit var providerRepository: ProviderRepository

    @Test
    fun `모든 데이터 조회`() {

        val flux = providerRepository.findAll();
        println(flux)
    }
}

