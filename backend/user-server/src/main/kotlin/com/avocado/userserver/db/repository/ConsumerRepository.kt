package com.avocado.userserver.db.repository

import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.db.entity.Consumer
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConsumerRepository : CoroutineCrudRepository<Consumer, ByteArray> {

    suspend fun findBySubAndSocial(sub:String, social:SocialType): Consumer?

    suspend fun save(consumer: Consumer): Consumer

}