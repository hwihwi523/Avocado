package com.avocado.userserver.db.entity

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.api.service.SocialType
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

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