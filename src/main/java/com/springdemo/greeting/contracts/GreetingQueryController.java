package com.springdemo.greeting.contracts;

import com.springdemo.Interceptors.Audit;
import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import com.vsolv.appframework.http.request.GetJsonRequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;


@RestController
@RequestMapping("/api/qry")
public class GreetingQueryController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private QueryHandler<GreetingQuery, GreetingQueryResult> greetingQryHandler;

    @Audit("HEALTHCHECK")
    @GetMapping(value = "/")
    public String serviceHealth() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");

        return "{\"data\":\"Okay! From " + this.getClass().getName() + " - " + Calendar.getInstance().getTime().toString() + "\"}";
    }

    @Audit("GREETING")
    @GetMapping(value = "/greeting",
            produces = "application/json")
    public @ResponseBody
    GreetingQueryResult greeting(@GetJsonRequestParam GreetingQuery qry) {

        logger.info(qry.getClass().getSimpleName());

        return greetingQryHandler.execute(qry);
    }
}