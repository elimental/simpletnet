package com.getjavajob.simplenet.web.converters;

import org.springframework.format.Formatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        java.util.Date parsed = df.parse(text);
        return new Date(parsed.getTime());
    }

    @Override
    public String print(Date object, Locale locale) {
        return null;
    }
}
