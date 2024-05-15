package edu.mci.fooddirector.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeToStringConverter {
    public static String convert(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
