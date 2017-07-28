package com.springdemo.greeting.contracts.queries;

import com.springdemo.shared.models.Address;
import com.vsolv.appframework.cqrs.query.BaseQuery;

public class GreetingQuery extends BaseQuery {
    private String name = null;
    private Address address = null;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}


