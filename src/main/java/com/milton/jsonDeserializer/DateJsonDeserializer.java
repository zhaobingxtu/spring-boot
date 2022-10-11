package com.milton.jsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.milton.util.DateUtils;

import java.util.Date;

public class DateJsonDeserializer extends JsonDeserializer<Date> {
    public DateJsonDeserializer() {
    }

    public Date deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            return DateUtils.dateDeserializer(p.getText());
        } catch (Exception var4) {
            return null;
        }
    }
}
