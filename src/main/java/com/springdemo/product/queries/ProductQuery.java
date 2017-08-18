package com.springdemo.product.queries;

import com.springdemo.shared.models.Product;
import com.vsolv.appframework.cqrs.query.Query;

public class ProductQuery implements Query {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
