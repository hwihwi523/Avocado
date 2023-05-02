package com.avocado.userserver.db.repository

import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.db.entity.Consumer
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConsumerRepository : CoroutineCrudRepository<Consumer, ByteArray> {

    suspend fun findBySubAndSocial(sub:String, social:SocialType): Consumer?

    suspend fun save(consumer: Consumer): Consumer

    @Modifying
    @Query("INSERT INTO consumer (id, name, email, picture_url, created_at, updated_at, sub, social, auth)" +
            " VALUES(:id, :name, :email, :pictureUrl, :createdAt, :updatedAt, :sub, :social, :auth)")
    suspend fun create(consumer: Consumer)
}