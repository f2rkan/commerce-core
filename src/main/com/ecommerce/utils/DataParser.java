package main.com.ecommerce.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataParser {

    public static LocalDate parseDate(String dateString, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + dateString);
            return null;
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid datetime format: " + dateTimeString);
            return null;
        }
    }

    public static int parseInt(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer format: " + intString);
            return -1;
        }
    }

    public static double parseDouble(String doubleString) {
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid double format: " + doubleString);
            return -1.0; 
        }
    }
}
