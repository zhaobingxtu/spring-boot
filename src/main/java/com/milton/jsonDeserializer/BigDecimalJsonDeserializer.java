package com.milton.jsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.math.BigDecimal;

public class BigDecimalJsonDeserializer extends JsonDeserializer<BigDecimal> {
    public BigDecimalJsonDeserializer() {
    }

    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return new BigDecimal(jsonParser.getText());
        } catch (Exception var4) {
            return null;
        }
    }
}
