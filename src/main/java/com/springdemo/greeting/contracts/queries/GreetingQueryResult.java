package com.springdemo.greeting.contracts.queries;

import com.springdemo.service.contracts.query.BaseQueryResult;

public class GreetingQueryResult extends BaseQueryResult {
    public GreetingQueryResult(String content, int returnCode) {
        super(content, returnCode);
    }
}
