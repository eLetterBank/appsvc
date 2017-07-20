package com.springdemo.greeting.contracts.queries;

import com.vsolv.appframework.cqrs.query.QueryResult;

public class GreetingQueryResult implements QueryResult {
    private final String content;
    private final int returnCode;

    public GreetingQueryResult(String content, int returnCode) {
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
