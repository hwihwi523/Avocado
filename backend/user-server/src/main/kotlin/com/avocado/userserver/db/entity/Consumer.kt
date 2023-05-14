package com.avocado.userserver.db.entity

import com.avocado.userserver.api.request.ConsumerNotRequiredInfoReq
import com.avocado.userserver.api.request.ConsumerPersonalColorReq
import com.avocado.userserver.api.request.ConsumerRequiredInfoReq
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
    val ageGroup: Int?, // 받아야 하는 값
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
    fun updateRequiredInfo(req: ConsumerRequiredInfoReq):Consumer {
        return Consumer(
            consumerId, name, email, pictureUrl,
            checkGender(req.gender),
            checkAgeGroup(req.ageGroup),
            createdAt, LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            height, weight, sub, social, mbtiId, personalColorId, 1
        )
    }

    fun updateNotRequiredInfo(req: ConsumerNotRequiredInfoReq):Consumer {
        val cHeight = checkIntValue(req.height)
        val cWeight = checkIntValue(req.weight)
        val cMbtiId = checkIntValueLimit(req.mbtiId, 15)

        return Consumer(
            consumerId, name, email, pictureUrl, gender, ageGroup,
            createdAt, LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            cHeight, cWeight, sub, social, cMbtiId, personalColorId, auth
        )
    }

    fun updatePersonalColor(req: ConsumerPersonalColorReq):Consumer {
        val cPersonalColorId = checkIntValueLimit(req.personalColorId, 9)
        return Consumer(
            consumerId, name, email, pictureUrl, gender, ageGroup, createdAt,
            LocalDateTime.now(ZoneId.of("Asia/Seoul")), height, weight, sub, social,
            mbtiId, cPersonalColorId, auth
        )
    }

    fun updateInfo(req: ConsumerUpdateReq):Consumer {
        val cHeight = checkIntValue(req.height)
        val cWeight = checkIntValue(req.weight)
        val cMbtiId = checkIntValueLimit(req.mbtiId, 15)
        val cAgeGroup = checkAgeGroup(req.ageGroup)
        val cPersonalColorId = checkIntValueLimit(req.personalColorId, 9)
        return Consumer(
            consumerId, name, email, pictureUrl, checkGender(req.gender),
            cAgeGroup, createdAt, LocalDateTime.now(ZoneId.of("Asia/Seoul")), cHeight, cWeight,
            sub, social, cMbtiId, cPersonalColorId, 1
        )
    }

    private fun checkAgeGroup(value:Int?):Int? {
        val cValue:Int = value?:return null
        return when(cValue) {
            10 -> cValue
            20 -> cValue
            30 -> cValue
            40 -> cValue
            50 -> cValue
            60 -> cValue
            70 -> cValue
            else -> throw BaseException(ResponseCode.INVALID_VALUE)
        }

    }
    private fun checkIntValue(value:Int?):Int? {
        val cValue:Int = value?:return null
        if (cValue == -1) {
            return null
        }
        if (cValue < 0) {
            throw BaseException(ResponseCode.INVALID_VALUE)
        }
        return cValue
    }

    private fun checkIntValueLimit(value:Int?, limit:Int):Int? {
        val cValue:Int = value?:return null
        if (cValue == -1) {
            return null
        }
        if ((cValue < 0) or (cValue > limit)) {
            throw BaseException(ResponseCode.INVALID_VALUE)
        }

        return cValue
    }

    private fun checkGender(value:String):String {
        return when(value) {
            "F"-> value
            "M"-> value
            else -> throw BaseException(ResponseCode.INVALID_VALUE)
        }
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