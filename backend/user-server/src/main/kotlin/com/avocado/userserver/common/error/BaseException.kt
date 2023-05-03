package com.avocado.userserver.common.error

import org.springframework.http.HttpStatus

class BaseException(status: HttpStatus, msg: String):RuntimeException() {
    public val status: HttpStatus = status
    public val msg: String = msg

    constructor(code: ResponseCode): this(code.status, code.msg)
}