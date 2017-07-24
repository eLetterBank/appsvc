package com.springdemo.greeting.contracts.commands;

import com.springdemo.greeting.contracts.shared.Address;
import com.vsolv.appframework.cqrs.command.BaseCommand;

public class AddGreetingCommand extends BaseCommand {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address address;
}
