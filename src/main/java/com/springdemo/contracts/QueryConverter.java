package com.springdemo.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueryConverter implements Converter<String, GreetingQuery> {

    @Override
    public GreetingQuery convert(String queryString) {
        ObjectMapper mapper = new ObjectMapper();
        GreetingQuery query = null;
        try {
            query = mapper.readValue(queryString, GreetingQuery.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return query;
    }
}
