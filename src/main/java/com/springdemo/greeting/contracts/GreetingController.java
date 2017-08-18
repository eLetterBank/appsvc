package com.springdemo.greeting.contracts;

import com.springdemo.greeting.contracts.commands.AddGreetingCommand;
import com.springdemo.greeting.contracts.commands.AddGreetingCommandResult;
import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.springdemo.interceptors.audit.Audit;
import com.vsolv.appframework.cqrs.command.CommandHandler;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import com.vsolv.appframework.http.request.GetJsonRequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/api/v1")
public class GreetingController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private QueryHandler<GreetingQuery, GreetingQueryResult> greetingQryHandler;

    @Autowired
    private CommandHandler<AddGreetingCommand, AddGreetingCommandResult> greetingCmdHandler;

    @Audit("HEALTH-CHECK")
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
    @GetMapping(value = "/greeting", produces = "application/json")
    public @ResponseBody
    GreetingQueryResult greeting(@GetJsonRequestParam GreetingQuery qry) {

        logger.info(qry.getClass().getSimpleName());
        return greetingQryHandler.execute(qry);
    }

    @Audit("ADD-GREETING")
    @PostMapping(value = "/addGreeting", produces = "application/json")
    public @ResponseBody
    AddGreetingCommandResult addGreeting(@RequestBody AddGreetingCommand cmd) {
        logger.debug(cmd);
        return greetingCmdHandler.execute(cmd);
    }
}