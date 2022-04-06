package ru.borisof.hessianman.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.var;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapValueRawJsonDeserializer extends JsonDeserializer<Map<String, Object>> {


    @Override
    public Map<String, Object> deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws
            IOException,
            JacksonException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        Map<String, Object> map = new HashMap<>();

        var fields = jsonNode.fields();

        while (fields.hasNext()) {
            var entry = fields.next();
            var node = entry.getValue();
            Object val;

            if (node.isLong()) {
                val = node.asLong();
            } else if (node.isInt()) {
                val = node.asInt();
            } else if (node.isTextual()) {
                val = node.asText();
            } else if (node.isDouble()) {
                val = node.asDouble();
            } else {
                val = node.toString();
            }
            map.put(entry.getKey(), val);
        }

        return map;
    }
}
