package com.avocado.community.common.error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {
    public HttpStatus status;
    public String msg;

    public BaseException(ResponseCode code) {
        this.status = code.status;
        this.msg = code.msg;
    }

}
