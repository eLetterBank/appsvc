package com.springdemo.greeting.contracts.shared;

public class Address {
    private String street = null;

    void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreet() {
        return this.street;
    }
}
