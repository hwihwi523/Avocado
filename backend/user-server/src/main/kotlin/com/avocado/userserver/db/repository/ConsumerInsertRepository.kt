package com.avocado.userserver.db.repository

import com.avocado.userserver.db.entity.Consumer
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ConsumerInsertRepository (
    val client: DatabaseClient
) {
    @Transactional
    suspend fun insert(consumer: Consumer) {
        client.sql("INSERT INTO consumer (id, name, email, picture_url, created_at, updated_at, sub, social, auth)" +
                " VALUES(:id, :name, :email, :pictureUrl, :createdAt, :updatedAt, :sub, :social, :auth)")
            .bind("id", consumer.consumerId)
            .bind("name", consumer.name)
            .bind("email", consumer.email)
            .bind("pictureUrl", consumer.pictureUrl)
            .bind("createdAt", consumer.createdAt)
            .bind("updatedAt", consumer.updatedAt)
            .bind("sub", consumer.sub)
            .bind("social", consumer.social)
            .bind("auth", consumer.auth)
            .fetch()
            .rowsUpdated()
            .awaitFirst()

    }

    @Transactional
    suspend fun insertWallet(consumer: Consumer) {
        client.sql("INSERT INTO wallet (consumer_id) VALUES (:id)")
            .bind("id", consumer.consumerId)
            .fetch()
            .rowsUpdated()
            .awaitFirst()
    }
}