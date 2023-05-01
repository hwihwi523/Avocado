package com.avocado.userserver.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("consumer")
data class Consumer (
    @Id
    val id: ByteArray,
    val name: String,
    val email: String,
    val pictureUrl: String,
    val gender: String,
    val age: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val height: Int,
    val weight: Int,
    val sub: String,
    val social: String,
    val mbtiId: Int,
    val personalColorId: Int

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Consumer

        if (!id.contentEquals(other.id)) return false

        return true
    }

    override fun hashCode(): Int {
        return id.contentHashCode()
    }

}