package com.springdemo.greeting.contracts;

import com.springdemo.greeting.contracts.commands.AddGreetingCommand;
import com.springdemo.greeting.contracts.commands.AddGreetingCommandResult;
import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.vsolv.appframework.cqrs.command.CommandHandler;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import com.vsolv.appframework.http.request.GetJsonRequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
public class GreetingController {

    private final Logger logger = LogManager.getRootLogger();

    @Autowired
    private QueryHandler<GreetingQuery, GreetingQueryResult> greetingQryHandler;

    @Autowired
    private CommandHandler<AddGreetingCommand, AddGreetingCommandResult> greetingCmdHandler;

    @GetMapping(value = "/")
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

    @GetMapping(value = "/greeting",
            produces = "application/json")
    public
    @ResponseBody
    GreetingQueryResult greeting(@GetJsonRequestParam GreetingQuery qry) {

        logger.info(qry);

        return greetingQryHandler.execute(qry);
    }

    @PostMapping(value = "/addGreeting",
            produces = "application/json")
    public
    @ResponseBody
    AddGreetingCommandResult addGreeting(@RequestBody AddGreetingCommand cmd) {

        logger.error(cmd);

        return greetingCmdHandler.execute(cmd);
    }
}