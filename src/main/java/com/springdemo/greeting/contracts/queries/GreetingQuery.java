package com.springdemo.greeting.contracts.queries;

import com.springdemo.greeting.contracts.shared.Address;
import com.vsolv.appframework.cqrs.query.BaseQuery;

public class GreetingQuery extends BaseQuery {
    private String name;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}


