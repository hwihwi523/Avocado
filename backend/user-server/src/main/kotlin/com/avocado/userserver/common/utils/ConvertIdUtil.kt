package com.avocado.userserver.common.utils

import org.springframework.stereotype.Component

@Component
class ConvertIdUtil {

    suspend fun unHex(uuid: String): ByteArray {
        check(uuid.length % 2 == 0) { "Must have an even length"}

        return uuid.chunked(2)
            .map {it.toInt(16).toByte()}
            .toByteArray()
    }

    suspend fun hex(id: ByteArray): String {
        return id.joinToString(separator = "") {
            eachByte -> "%02x".format(eachByte)
        }
    }
}