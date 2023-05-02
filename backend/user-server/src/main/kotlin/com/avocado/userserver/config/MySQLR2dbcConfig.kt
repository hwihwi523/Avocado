package com.avocado.userserver.config

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

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

        return MySqlConnectionFactory.from(
                    MySqlConnectionConfiguration.builder()
                .host(host).username(username).password(password)
                        .port(port).database(database).build()
        )
    }

    /**
     * @Transactional annotation을 사용하기 위해 Bean 등록
     */
    @Bean
    fun reactiveTransactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
