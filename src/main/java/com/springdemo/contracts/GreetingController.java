package com.springdemo.contracts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class GreetingController {

    private final Logger logger = LogManager.getRootLogger();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {

        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

        String timeStamp = Calendar.getInstance().getTime().toString();

        logger.info("Time Stamp : " + timeStamp);
        logger.info("Generate response with server time stamp");

        return "{\"data\":\"Hello Docker World! - " + timeStamp + "\"}";
    }

    @RequestMapping(value = "/greeting",
            method = RequestMethod.GET,
            consumes = "application/json",
            produces = "application/json")
    public
    @ResponseBody
    GreetingQueryResult greeting(GreetingQuery greetingQuery) {

        logger.error(greetingQuery);

        String name = "";

        if (greetingQuery == null || greetingQuery.getName() == null) name = "World";
        else name = greetingQuery.getName();

        String timeStamp = Calendar.getInstance().getTime().toString();
        String responseData = "Hello " + name + "! - " + timeStamp;

        return new GreetingQueryResult(responseData);
    }

}