package com.avocado.userserver.api.service

import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.ConvertIdUtil
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
    private val providerRepository: ProviderRepository,
    private val convertIdUtil: ConvertIdUtil
) {

    @Test
    fun `제공자 Access Token 출력`() {
        runBlocking{
            val id:ByteArray = convertIdUtil.unHex("A15DEE5DE3FF11ED89BF8CB0E9DBB87D")
            val member: Provider = providerRepository.findById(id)?: throw BaseException(ResponseCode.INVALID_VALUE)
            println(jwtProvider.getAccessToken(member))
        }
    }

    @Test
    fun `JWT Token 확인`() {
        runBlocking {
            println(jwtProvider.parseClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImQyZThhYzA1YjI0MjQzYWY4MzliNzUzZTRhZGVlNTc2IiwidHlwZSI6ImNvbnN1bWVyIn0.55k0PHRPcO8KSh2POvfQuU1_VP3JYxqR1oCWL-RVkMM"))
            println(jwtProvider.parseClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25hbF9jb2xvcl9pZCI6LTEsInBpY3R1cmVfdXJsIjoiaHR0cDovL2sua2FrYW9jZG4ubmV0L2RuL1V3c1U2L2J0cjBSSko5OGd2L25jd2RmT3hDSjdxdmxLczhaaTBpSzEvaW1nXzExMHgxMTAuanBnIiwibWJ0aV9pZCI6LTEsIndlaWdodCI6LTEsImlkIjoiZDJlOGFjMDViMjQyNDNhZjgzOWI3NTNlNGFkZWU1NzYiLCJ0eXBlIjoiY29uc3VtZXIiLCJlbWFpbCI6ImhlbGVuYWxpbTdAbmF2ZXIuY29tIiwiYWdlIjotMSwiaGVpZ2h0IjotMX0.TNdQkmGWD9J9bitC0Sk8xDT5zfDpENIXyMemTLwH43c"))
        }
    }

    @Test
    fun `parseClaim`() {
        runBlocking {
            jwtProvider.parseClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjI0ZTI1OGFmNWIzMTRiZjE4MjY4OGYwZWZhY2IxZjA0IiwidHlwZSI6ImNvbnN1bWVyIiwiaXNzIjoiYXZvY2Fkby5jb20iLCJpYXQiOjE2ODM0NjA3MDMsImV4cCI6MTY4MzQ4MjMwM30.umNdT6Acuq1bedI5nj2tIhbT9PiwccaayCANyHkYUoQ");
            jwtProvider.parseClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwZXJzb25hbF9jb2xvcl9pZCI6LTEsInBpY3R1cmVfdXJsIjoiaHR0cDovL2sua2FrYW9jZG4ubmV0L2RuL1V3c1U2L2J0cjBSSko5OGd2L25jd2RmT3hDSjdxdmxLczhaaTBpSzEvaW1nXzExMHgxMTAuanBnIiwiZ2VuZGVyIjoiTkFOIiwibWJ0aV9pZCI6LTEsImFnZV9ncm91cCI6LTEsIm5hbWUiOiLsnoTtmJzsp4QiLCJ3ZWlnaHQiOi0xLCJpZCI6IjI0ZTI1OGFmNWIzMTRiZjE4MjY4OGYwZWZhY2IxZjA0IiwidHlwZSI6ImNvbnN1bWVyIiwiZW1haWwiOiJoZWxlbmFsaW03QG5hdmVyLmNvbSIsImhlaWdodCI6LTEsImlzcyI6ImF2b2NhZG8uY29tIiwiaWF0IjoxNjgzNDYwNzAzLCJleHAiOjE2ODQ3NTY3MDN9.QfJ_evQDLDJbrqWZbnrQ3-fijJM26ucGboK3-6K2INM");
        }
    }
}