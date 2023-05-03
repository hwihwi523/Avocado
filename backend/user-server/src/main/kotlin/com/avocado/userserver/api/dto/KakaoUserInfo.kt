package com.avocado.userserver.api.dto

import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.db.entity.Consumer
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

data class KakaoUserInfo(
    val aud: String,
    val sub: String,
    val exp: Long,
    val iat: Long,
    val auth_time: Long,
    val iss: String,
    val nickname: String,
    val picture: String,
    val email: String
) {
    fun toConsumer(): Consumer {
        var id = UUID.randomUUID().toString().replace("-","").decodeHex()
        return Consumer(
            id,
            nickname,
            email,
            picture,
            null,
            null,
            LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            null,
            null,
            sub,
            SocialType.KAKAO,
            null,
            null,
            0
        )
    }
    private fun String.decodeHex(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }

        return chunked(2)
            .map{ it.toInt(16).toByte() }
            .toByteArray()
    }
}
