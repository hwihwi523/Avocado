package com.avocado.userserver.api.controller

import com.avocado.userserver.api.response.TokenRefreshResp
import com.avocado.userserver.api.service.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController (
    val tokenService: TokenService,
) {
    @GetMapping("/refresh")
    suspend fun refresh(request: ServerHttpRequest):ResponseEntity<TokenRefreshResp> {
        return ResponseEntity.ok(tokenService.refresh(request))
    }
}