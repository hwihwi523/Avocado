package com.avocado.userserver.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("wallet")
class Wallet(
    @Id
    val consumerId: ByteArray,
    val totalExpense: Long,
    val balance: Long,
    val updatedAt: LocalDateTime,
    ) {


}