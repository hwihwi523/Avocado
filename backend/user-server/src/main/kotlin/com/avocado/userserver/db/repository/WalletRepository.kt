package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Wallet
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : CoroutineCrudRepository<Wallet, ByteArray>{

    suspend fun findByConsumerId(consumerId:ByteArray): Wallet?

    suspend fun save(wallet: Wallet): Wallet
}