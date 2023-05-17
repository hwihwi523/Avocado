package com.avocado.userserver.api.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(ConsumerService::class)
@ExtendWith(SpringExtension::class)
internal class ConsumerServiceTest @Autowired constructor(
    private val consumerService: ConsumerService,
) {

    val userId = "24E258AF-5B31-4BF1-8268-8F0EFACB1F04"

    @Test
    fun `구매금액 update 확인`() {
        runBlocking {
            consumerService.updateWallet(200L, userId)
        }
    }
}