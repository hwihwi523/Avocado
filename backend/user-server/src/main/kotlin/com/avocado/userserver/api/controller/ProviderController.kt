package com.avocado.userserver.api.controller

import com.avocado.userserver.api.request.ProviderLoginReq
import com.avocado.userserver.api.service.ProviderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProviderController @Autowired constructor(
    private val providerService: ProviderService
) {
    val log: Logger = LoggerFactory.getLogger(ProviderController::class.java)

    @PostMapping("/provider/login")
    suspend fun login(@RequestBody req: ProviderLoginReq): ResponseEntity<Any> {
        log.info("판매자 로그인 요청. req: {}", req)
        return ResponseEntity.ok().body(providerService.login(req))
    }
}