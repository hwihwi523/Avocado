package com.avocado.userserver.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class OAuthController {

    @GetMapping(path = ["/test"])
    @ResponseBody
    fun getNumbers() = Flux.range(1, 100)

}