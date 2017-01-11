package com.springdemo.contracts;

public class GreetingQueryResult {
    private final String content;

    public GreetingQueryResult(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
