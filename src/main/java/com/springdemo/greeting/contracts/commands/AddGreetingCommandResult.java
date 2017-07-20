package com.springdemo.greeting.contracts.commands;

import com.vsolv.appframework.cqrs.command.CommandResult;

public class AddGreetingCommandResult implements CommandResult{
    private final String content;
    private final int returnCode;

    public AddGreetingCommandResult(String content, int returnCode) {
        this.content = content;
        this.returnCode = returnCode;
    }

    public String getContent() {
        return this.content;
    }

    public int getReturnCode() {
        return this.returnCode;
    }
}
