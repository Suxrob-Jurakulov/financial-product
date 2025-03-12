package com.company.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Sukhrob
 */

public class JsonHelper {
    
    public JsonHelper() {
    }

    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static String get(Object obj) {
        if (obj != null) {
            try {
                return getMapper().writeValueAsString(obj);
            } catch (IOException var2) {
                System.out.println(var2.getMessage());
            }
        }

        return null;
    }

}
