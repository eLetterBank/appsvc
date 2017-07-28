package com.springdemo.greeting.handlers;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.greeting.contracts.commands.AddGreetingCommand;
import com.springdemo.greeting.contracts.commands.AddGreetingCommandResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.vsolv.appframework.cqrs.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AddGreetingCommandHandler implements CommandHandler<AddGreetingCommand, AddGreetingCommandResult> {

    @Autowired
    private TimeRepository timeRepo;

    @Override
    public AddGreetingCommandResult execute(AddGreetingCommand cmd) {
        String responseData = "Hello " + cmd.getName() + " "
                + cmd.getAddress().getStreet()
                + "! - "
                + timeRepo.getServerTime().toString();

        return new AddGreetingCommandResult(responseData, ReturnCodes.SUCCESS.getId());
    }
}
