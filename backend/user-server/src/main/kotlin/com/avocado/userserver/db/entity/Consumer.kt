package com.avocado.userserver.db.entity

import com.avocado.userserver.api.request.ConsumerAddInfoReq
import com.avocado.userserver.api.request.ConsumerUpdateReq
import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.ZoneId

@Table("consumer")
data class Consumer (
    @Id
    @Column("id")
    val consumerId: ByteArray, // 필수
    val name: String, // 필수
    val email: String, // 필수
    val pictureUrl: String, // 필수
    val gender: String?, // 받아야 하는 값
    val age: Int?, // 받아야 하는 값
    val createdAt: LocalDateTime, // 필수
    val updatedAt: LocalDateTime, // 필수
    val height: Int?, // 없을 수 있는 값
    val weight: Int?, // 없을 수 있는 값
    val sub: String, // 필수
    val social: SocialType, // 필수
    val mbtiId: Int?, // 받아야 하는 값
    val personalColorId: Int?, // 받아야 하는 값
    val auth: Int,
)
{
    fun addInfo(req: ConsumerAddInfoReq):Consumer {
        return Consumer(
            consumerId,
            name,
            email,
            pictureUrl,
            req.gender,
            req.age,
            createdAt,
            LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            req.height,
            req.weight,
            sub,
            social,
            req.mbtiId,
            req.personalColorId,
            1
        )
    }

    fun updateInfo(req: ConsumerUpdateReq):Consumer {
        return Consumer(
            consumerId,
            name,
            email,
            pictureUrl,
            if ((req.gender == "M") or (req.gender == "F")) {req.gender} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            if (req.age > 0) {req.age} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            createdAt,
            LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            if (req.height > 0) {req.height} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            if (req.weight > 0) {req.weight} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            sub,
            social,
            if ((0<= req.mbtiId) and (req.mbtiId <= 15)) {req.mbtiId} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            if ((0<= req.personalColorId) and (req.personalColorId <= 9)) {req.personalColorId} else {throw BaseException(ResponseCode.INVALID_VALUE)},
            auth
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Consumer

        if (!consumerId.contentEquals(other.consumerId)) return false

        return true
    }

    override fun hashCode(): Int {
        return consumerId.contentHashCode()
    }

}