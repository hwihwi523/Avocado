package com.avocado.userserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
@ComponentScan("com.avocado")
class UserServerApplication

fun main(args: Array<String>) {
    runApplication<UserServerApplication>(*args)
}
