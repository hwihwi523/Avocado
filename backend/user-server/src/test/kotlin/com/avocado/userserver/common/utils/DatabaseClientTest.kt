package com.avocado.userserver.common.utils

import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.db.entity.Provider
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*
import java.util.function.BiFunction


@WebFluxTest(DatabaseClient::class)
@ExtendWith(SpringExtension::class)
class DatabaseClientTest @Autowired constructor(
    private val client: DatabaseClient
) {
    val MAPPING_FUNCTION: BiFunction<Row, RowMetadata, Provider> =
        BiFunction<Row, RowMetadata, Provider> { row: Row, rowMetaData: RowMetadata? ->
            Provider(
                row.get("id", ByteArray::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE),
                row.get("name", String::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE),
                row.get("email", String::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE),
                row.get("password", String::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE),
                row.get("created_at", LocalDateTime::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE),
                row.get("updated_at", LocalDateTime::class.java)?:throw BaseException(ResponseCode.INVALID_VALUE)
            )
        }


    @Test
    fun `SELECT 작동 테스트`() {
        runBlocking {
            println(client.sql("SELECT * FROM provider").map(MAPPING_FUNCTION).all().awaitFirst())
        }
    }

    @Test
    fun `CREATE 작동 테스트`() {
        runBlocking {
            client.sql("INSERT INTO mbti (id, kind) VALUES(1, 'INFJ')")
                .fetch()
                .rowsUpdated()
                .awaitFirst()
        }
    }


}