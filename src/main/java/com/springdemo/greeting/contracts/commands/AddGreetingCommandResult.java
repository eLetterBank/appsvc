package com.springdemo.greeting.contracts.commands;

import com.vsolv.appframework.cqrs.command.CommandResult;

public class AddGreetingCommandResult implements CommandResult{
    public String getContent() {
        return status;
    }

    private final String status;

    public AddGreetingCommandResult(String content) {
        this.status = content;
    }
}
