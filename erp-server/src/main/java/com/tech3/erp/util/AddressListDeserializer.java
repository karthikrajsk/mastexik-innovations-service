package com.tech3.erp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech3.erp.dto.AddressDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressListDeserializer extends JsonDeserializer<List<AddressDTO>> {

    @Override
    public List<AddressDTO> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        
        List<AddressDTO> addresses = new ArrayList<>();
        
        if (node.isObject()) {
            AddressDTO address = mapper.treeToValue(node, AddressDTO.class);
            addresses.add(address);
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                AddressDTO address = mapper.treeToValue(item, AddressDTO.class);
                addresses.add(address);
            }
        }
        
        return addresses;
    }
}
