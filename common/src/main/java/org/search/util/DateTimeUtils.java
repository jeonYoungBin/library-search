package org.search.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseOffsetDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
