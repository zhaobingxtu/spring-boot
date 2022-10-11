package com.milton.jsonDeserializer;

import com.milton.util.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    public DateConverter() {
    }

    public Date convert(String dateValue) {
        return DateUtils.dateDeserializer(dateValue);
    }
}
