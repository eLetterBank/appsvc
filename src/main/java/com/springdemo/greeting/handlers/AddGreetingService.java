package com.springdemo.greeting.handlers;

import com.springdemo.greeting.contracts.commands.AddGreetingCommand;
import com.springdemo.greeting.contracts.commands.AddGreetingCommandResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.vsolv.appframework.cqrs.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AddGreetingService implements CommandHandler<AddGreetingCommand, AddGreetingCommandResult> {

    @Autowired
    private TimeRepository timeRepo;

    @Override
    public AddGreetingCommandResult execute(AddGreetingCommand cmd) {
        String timeStamp = timeRepo.getServerTime().toString();
        String responseData = "Hello " + cmd.getName() + " " + cmd.getAddress().getStreet() + "! - " + timeStamp;

        return new AddGreetingCommandResult(responseData);
    }
}
