package io.github.darshan744.nebula.Http.HttpRequest.Parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NebulaJsonParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T getObjectOfTheBody(String requstBodyString, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException {

        return objectMapper.readValue(requstBodyString, clazz);
    }

    public String serializeObject(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
