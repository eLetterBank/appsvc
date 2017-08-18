package com.springdemo.card.contracts;

import com.springdemo.card.contracts.query.CheckCardQuery;
import com.springdemo.card.contracts.query.CheckCardQueryResult;
import com.springdemo.interceptors.audit.Audit;
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
@RequestMapping("/api/card")
public class CardController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    QueryHandler<CheckCardQuery, CheckCardQueryResult> queryHandler;

    @Audit("CARD-CHECK")
    @GetMapping(value = "/chk", produces = "application/json")
    public @ResponseBody
    CheckCardQueryResult productDetails(@GetJsonRequestParam CheckCardQuery qry) {

        logger.info(qry.getClass().getSimpleName());

        return queryHandler.execute(qry);
    }
}
