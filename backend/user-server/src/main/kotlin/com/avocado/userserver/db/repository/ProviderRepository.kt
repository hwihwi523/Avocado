package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Provider
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProviderRepository : CoroutineCrudRepository<Provider, ByteArray>
{
    suspend fun findByEmail(email:String): Provider?
}