package com.avocado.userserver.config

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2dbcConfig : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {

        return MySqlConnectionFactory.from(
                    MySqlConnectionConfiguration.builder()
                .host("localhost").password("ssafy").port(3306).database("avocado_merchandise")
                .username("root").build()
        )
    }
}
