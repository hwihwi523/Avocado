package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Provider
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(ProviderRepository::class)
@ExtendWith(SpringExtension::class)
class ProviderRepositoryTest @Autowired constructor(
    private val providerRepository: ProviderRepository
) {
    @Test
    fun `조회 테스트`() {
        runBlocking {
            val id:ByteArray = "A15DEE5DE3FF11ED89BF8CB0E9DBB87D".decodeHex()
            println(id)
            val member: Provider? = providerRepository.findById(id)
            println(member)
        }
    }

    private fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map{ it.toInt(16).toByte() }
            .toByteArray()
    }
}