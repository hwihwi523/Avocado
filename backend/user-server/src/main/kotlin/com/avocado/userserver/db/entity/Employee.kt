package com.avocado.userserver.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Employee(
    @Id
    val id: String? = null,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()

}