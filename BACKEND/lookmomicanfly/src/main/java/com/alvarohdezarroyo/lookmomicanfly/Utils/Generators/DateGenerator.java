package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateGenerator {

    public static String formatDate(LocalDateTime date){
        final DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        return dateTimeFormatter.format(date);
    }

}
