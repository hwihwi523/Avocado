package com.avocado.userserver.api.controller

import com.avocado.userserver.api.request.ConsumerNotRequiredInfoReq
import com.avocado.userserver.api.request.ConsumerPersonalColorReq
import com.avocado.userserver.api.request.ConsumerRequiredInfoReq
import com.avocado.userserver.api.request.ConsumerUpdateReq
import com.avocado.userserver.api.service.ConsumerService
import com.avocado.userserver.api.service.JwtProvider
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("consumer")
class ConsumerController(
    val consumerService: ConsumerService,
    val jwtProvider: JwtProvider
) {

    @PutMapping("signup/required-info")
    suspend fun addRequiredInfo(@RequestBody req: ConsumerRequiredInfoReq, request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.putRequiredInfo(req, claims)
        resultMap["msg"] = "필수정보_추가_성공"
        resultMap["status"] = 200
        return ResponseEntity.ok(resultMap)
    }

    @PutMapping("signup/not-required-info")
    suspend fun addNotRequiredInfo(@RequestBody req: ConsumerNotRequiredInfoReq, request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.putNotRequiredInfo(req, claims)
        resultMap["msg"] = "선택정보_추가_성공"
        resultMap["status"] = 200
        return ResponseEntity.ok(resultMap)
    }

    @PutMapping("personal-color")
    suspend fun updatePersonalColor(@RequestBody req: ConsumerPersonalColorReq, request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.updatePersonalColor(req, claims)
        resultMap["msg"] = "퍼스널컬러_갱신_성공"
        resultMap["status"] = 200
        return ResponseEntity.ok(resultMap)
    }

    @PutMapping
    suspend fun updateInfo(@RequestBody req: ConsumerUpdateReq, request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.updateInfo(req, claims)
        resultMap["msg"] = "정보수정_성공"
        resultMap["status"] = 200
        return ResponseEntity.ok(resultMap)

    }

    @DeleteMapping
    suspend fun withdraw(request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.deleteConsumer(claims)
        resultMap["msg"] = "회원탈퇴_성공"
        resultMap["status"] = 200
        return ResponseEntity.ok(resultMap)
    }
}