package com.avocado.userserver.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("provide")
class Provider (
    @Id
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)