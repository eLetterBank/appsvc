package com.springdemo.product;

import com.springdemo.interceptors.audit.Audit;
import com.springdemo.product.queries.ProductQuery;
import com.springdemo.product.queries.ProductQueryResult;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import com.vsolv.appframework.http.request.GetJsonRequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private QueryHandler<ProductQuery, ProductQueryResult> queryHandler;

    @Audit("PRODUCT")
    @GetMapping(value = "/product", produces = "application/json")
    public @ResponseBody
    ProductQueryResult productDetails(@GetJsonRequestParam ProductQuery qry) {

        logger.info(qry.getClass().getSimpleName());

        return queryHandler.execute(qry);
    }
}
