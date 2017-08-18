package com.springdemo.product.queries;

import com.springdemo.service.contracts.query.BaseQueryResult;

public class ProductQueryResult extends BaseQueryResult {
    public ProductQueryResult(String content, int returnCode) {
        super(content, returnCode);
    }
}
