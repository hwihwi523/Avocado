package com.avocado.userserver.api.controller

import com.avocado.userserver.api.request.ConsumerAddInfoReq
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

    @PostMapping("signup/add-info")
    suspend fun addInfo(@RequestBody req: ConsumerAddInfoReq, request: ServerHttpRequest): ResponseEntity<HashMap<String, Any>> {
        var resultMap = HashMap<String, Any>()
        val claims = jwtProvider.getClaims(request)
        consumerService.putAdditionalInfo(req, claims)
        resultMap["msg"] = "정보추가_성공"
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