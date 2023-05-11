package com.avocado.statistics.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public String getNowDate() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
