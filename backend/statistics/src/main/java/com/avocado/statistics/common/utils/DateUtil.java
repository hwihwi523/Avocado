package com.avocado.statistics.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class DateUtil {

    public String getNowDate() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static int getUnixDate() {
        LocalDate epoch = LocalDate.of(1970, 1, 1);
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return (int) ChronoUnit.DAYS.between(epoch, today);
    }
}
