package com.springdemo.greeting.contracts;

import com.springdemo.greeting.contracts.commands.AddGreetingCommand;
import com.springdemo.greeting.contracts.commands.AddGreetingCommandResult;
import com.vsolv.appframework.cqrs.command.CommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/api/cmd")
public class GreetingCommandController {
    private final Logger logger = LogManager.getRootLogger();

    @Autowired
    private CommandHandler<AddGreetingCommand, AddGreetingCommandResult> greetingCmdHandler;

    @GetMapping(value = "/")
    public String serviceHealth() {
        String timeStamp = Calendar.getInstance().getTime().toString();
        return "{\"data\":\"Hello World! From: " + this.getClass().getName() + " - " + timeStamp + "\"}";
    }

    @PostMapping(value = "/addGreeting",
            produces = "application/json")
    public
    @ResponseBody
    AddGreetingCommandResult addGreeting(@RequestBody AddGreetingCommand cmd) {

        logger.debug(cmd);

        return greetingCmdHandler.execute(cmd);
    }
}
