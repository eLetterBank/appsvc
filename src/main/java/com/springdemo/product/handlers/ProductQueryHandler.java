package com.springdemo.product.handlers;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.product.queries.ProductQuery;
import com.springdemo.product.queries.ProductQueryResult;
import com.springdemo.shared.models.ExecutionContext;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductQueryHandler implements QueryHandler<ProductQuery,ProductQueryResult> {
    @Autowired
    ExecutionContext executionContext;

    @Override
    public ProductQueryResult execute(ProductQuery productQuery) {

        String responseData = productQuery.getProduct().getProductId() + " - " + executionContext.getRequestId();

        return new ProductQueryResult(responseData, ReturnCodes.SUCCESS.getId());
    }
}
