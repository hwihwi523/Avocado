package com.avocado.userserver.api.service

import com.avocado.userserver.api.request.ProviderLoginReq
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.db.repository.ProviderRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

// TIP : 테스트 코드에서 함수를 private 처리를 하면, test 를 못 찾는다...
@WebFluxTest(ProviderService::class)
@ExtendWith(SpringExtension::class)
internal class ProviderServiceTest @Autowired constructor(
    private val providerService: ProviderService
) {
    
    @Nested
    @DisplayName("로그인 테스트")
    inner class LoginTest{

        private val noEmailReq = ProviderLoginReq("wrong@gmail.com", "avocado506")
        private val wrongPwdReq = ProviderLoginReq("avocado1@gmail.com", "wrong")
        private val rightPwdReq = ProviderLoginReq("avocado1@gmail.com", "avocado506")

        @Test
        fun `이메일이 없는 경우`() {
            runBlocking {
                val e = assertThrows<BaseException> {
                    providerService.login(noEmailReq)
                }
                assertEquals(HttpStatus.BAD_REQUEST, e.status)
            }
        }
        
        @Test
        fun `비밀번호가 일치하지 않는 경우`() {
            runBlocking {
                val e = assertThrows<BaseException> {
                    providerService.login(wrongPwdReq)
                }
                assertEquals(HttpStatus.BAD_REQUEST, e.status)
            }

        }
        
        @Test
        fun `성공적으로 로그인한 경우`() {
            runBlocking {
                println(providerService.login(rightPwdReq))
            }
        }

    }

}