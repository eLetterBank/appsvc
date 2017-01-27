package com.springdemo.greeting.queries;

import com.vsolv.appframework.cqrs.query.QueryResult;

public class GreetingQueryResult implements QueryResult {
    private final String content;

    public GreetingQueryResult(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
