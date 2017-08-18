package com.springdemo.card.contracts.query;

import com.springdemo.service.contracts.query.BaseQueryResult;

public class CheckCardQueryResult extends BaseQueryResult {
    public CheckCardQueryResult(String content, int returnCode) {
        super(content, returnCode);
    }
}
