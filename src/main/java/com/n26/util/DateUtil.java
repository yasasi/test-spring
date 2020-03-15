package com.n26.util;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DateUtil {
    private DateUtil() {
    }

    public static final LocalDateTime getDateFromString(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(timestamp, formatter);
    }

}
