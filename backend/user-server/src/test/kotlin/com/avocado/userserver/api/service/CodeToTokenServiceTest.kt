package com.avocado.userserver.api.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(CodeToTokenService::class)
@ExtendWith(SpringExtension::class)
internal class CodeToTokenServiceTest @Autowired constructor(
    private val codeToTokenService: CodeToTokenService
) {
    
    @Test
    fun `토큰 발급 테스트`() {
        println(codeToTokenService.codeToTokenKakao(""))
    }
}