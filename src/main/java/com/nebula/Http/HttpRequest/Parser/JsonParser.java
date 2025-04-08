package com.nebula.Http.HttpRequest.Parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebula.Logger.NebulaLogger;
import com.nebula.Logger.NebulaLoggerFactory;

public class JsonParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NebulaLogger logger = NebulaLoggerFactory.getLogger(JsonParser.class);
    public <T> T getObjectOfTheBody(String requstBodyString , Class<T> clazz) throws JsonMappingException, JsonProcessingException {
        logger.info("Parsing The body to CLASS : " + clazz.getName());
        return objectMapper.readValue(requstBodyString, clazz);
    }
}
