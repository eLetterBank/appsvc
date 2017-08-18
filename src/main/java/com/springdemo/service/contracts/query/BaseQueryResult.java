package com.springdemo.service.contracts.query;

import com.vsolv.appframework.cqrs.query.QueryResult;

public class BaseQueryResult implements QueryResult {
    private final String content;
    private final int returnCode;

    public BaseQueryResult(String content, int returnCode) {
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
