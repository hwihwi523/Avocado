package com.avocado.userserver.api.service

import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.db.entity.Provider
import com.avocado.userserver.db.repository.ProviderRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(JwtProvider::class)
@ExtendWith(SpringExtension::class)
internal class JwtProviderTest @Autowired constructor(
    private val jwtProvider: JwtProvider,
    private val providerRepository: ProviderRepository
) {

    @Test
    fun `제공자 Access Token 출력`() {
        runBlocking{
            val id:ByteArray = "A15DEE5DE3FF11ED89BF8CB0E9DBB87D".decodeHex()
            val member: Provider = providerRepository.findById(id)?: throw BaseException(ResponseCode.INVALID_VALUE)
            println(jwtProvider.getAccessToken(member))
        }
    }

    private fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map{ it.toInt(16).toByte() }
            .toByteArray()
    }


}