package com.ims.util;

import com.ims.service.InvoiceService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final double ZERO = 0.0;

    public static String getCurrentDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static String getOverdueDate(int days) {
        return getCurrentDate().plusDays(days).toString();
    }

    public static String getUniqueId() {
        long uuid = System.currentTimeMillis() + (long) (Math.random() * 1000);
        return String.valueOf(uuid);
    }

    public static double getInitialAmount(){
        return ZERO;
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}
