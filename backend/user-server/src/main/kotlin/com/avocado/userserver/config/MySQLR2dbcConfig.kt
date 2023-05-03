package com.avocado.userserver.config

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.ZoneId

@Configuration
@EnableTransactionManagement
class MySQLR2dbcConfig(
    @Value("\${r2dbc.host}")
    val host: String,
    @Value("\${r2dbc.username}")
    val username: String,
    @Value("\${r2dbc.password}")
    val password: String,
    @Value("\${r2dbc.port}")
    val port: Int,
    @Value("\${r2dbc.database}")
    val database: String,

) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val config = MySqlConnectionConfiguration.builder()
            .host(host)
            .user(username)
            .port(port)
            .password(password)
            .database(database)
            .serverZoneId(ZoneId.of("Asia/Seoul"))
            .build()

        return MySqlConnectionFactory.from(config)
    }

    /**
     * @Transactional annotation을 사용하기 위해 Bean 등록
     */
    @Bean
    fun reactiveTransactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
