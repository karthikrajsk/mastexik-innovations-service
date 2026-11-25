package com.tech3.erp.util.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tech3.erp.dto.PurchaseReturnFabricSummaryDTO;

import java.io.IOException;

/**
 * Accepts either an object or a single-element array for fabricSummary.
 */
public class PurchaseReturnFabricSummaryDeserializer extends JsonDeserializer<PurchaseReturnFabricSummaryDTO> {
    @Override
    public PurchaseReturnFabricSummaryDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken current = p.currentToken();
        if (current == JsonToken.START_ARRAY) {
            // If array, read first element as object; ignore others
            JsonNode node = p.getCodec().readTree(p);
            if (node.isArray() && node.size() > 0) {
                JsonNode first = node.get(0);
                return p.getCodec().treeToValue(first, PurchaseReturnFabricSummaryDTO.class);
            }
            return null;
        }
        // Fallback to standard object deserialization
        JsonNode node = p.getCodec().readTree(p);
        return p.getCodec().treeToValue(node, PurchaseReturnFabricSummaryDTO.class);
    }
}