package com.tech3.erp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class BooleanToIntegerDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();
        
        if (token.isBoolean()) {
            return p.getBooleanValue() ? 1 : 0;
        }
        if (token.isNumeric()) {
            return p.getIntValue();
        }
        if (token == JsonToken.VALUE_NULL) {
            return 0;
        }
        throw ctxt.weirdStringException(p.getText(), Integer.class, "Cannot deserialize value");
    }
}
