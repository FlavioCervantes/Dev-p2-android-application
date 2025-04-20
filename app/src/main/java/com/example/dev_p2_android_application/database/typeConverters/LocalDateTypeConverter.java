package com.example.dev_p2_android_application.database.typeConverters;

import java.time.LocalDate;

public class LocalDateTypeConverter {

    // Convert LocalDate to String
    public static String fromLocalDate(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    // Convert String to LocalDate
    public static LocalDate toLocalDate(String dateString) {
        return dateString != null ? LocalDate.parse(dateString) : null;
    }
}
