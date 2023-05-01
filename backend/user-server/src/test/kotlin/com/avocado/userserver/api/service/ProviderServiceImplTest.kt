package com.avocado.userserver.api.service

import com.avocado.userserver.db.entity.Provider
import com.avocado.userserver.db.repository.ProviderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

// TIP : 테스트 코드에서 함수를 private 처리를 하면, test 를 못 찾는다...
@WebFluxTest(ProviderService::class)
@ExtendWith(SpringExtension::class)
internal class ProviderServiceImplTest @Autowired constructor(
    private val providerService: ProviderService,
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