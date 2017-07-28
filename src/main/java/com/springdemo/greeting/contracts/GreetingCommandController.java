package com.springdemo.greeting.contracts;

import com.springdemo.interceptors.Audit;
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

    @Audit("HEALTH-CHECK")
    @GetMapping(value = "/")
    public String serviceHealth() {
        return "{\"data\":\"Okay! From " + this.getClass().getName() + " - "
                + Calendar.getInstance().getTime().toString() + "\"}";
    }

    @Audit("ADD-GREETING")
    @PostMapping(value = "/addGreeting",
            produces = "application/json")
    public @ResponseBody
    AddGreetingCommandResult addGreeting(@RequestBody AddGreetingCommand cmd) {
        logger.debug(cmd);
        return greetingCmdHandler.execute(cmd);
    }
}
