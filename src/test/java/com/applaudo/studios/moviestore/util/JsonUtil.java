package com.applaudo.studios.moviestore.util;

import com.applaudo.studios.moviestore.service.rest.StoreServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil
{
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String getJson(Object o)
    {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static void printJson(Object o)
    {
        var json = getJson(o);
        logger.info("Json: {}", json);
    }
}
