package com.springdemo.greeting.handlers;

import com.springdemo.exceptions.ReturnCodes;
import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class GreetingQueryHanlder implements QueryHandler<GreetingQuery, GreetingQueryResult> {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TimeRepository timeRepo;

    @Override
    public GreetingQueryResult execute(GreetingQuery qry) {

        if (!isValiedQry(qry)) {
            return new GreetingQueryResult(ReturnCodes.INVALID_REQUEST.getMessage(), ReturnCodes.INVALID_REQUEST.getId());
        }

        String streetName = null;

        if (qry.getAddress() != null) {
            logger.info("Address is avaiable.");
            if (qry.getAddress().getStreet() != null) {
                streetName = qry.getAddress().getStreet();
            }
        }

        String responseData = "Hello " + qry.getName() + " "
                + (streetName == null ? " - " : streetName)
                + "! - "
                + timeRepo.getServerTime().toString();

        return new GreetingQueryResult(responseData, ReturnCodes.SUCCESS.getId());
    }

    private boolean isValiedQry(GreetingQuery qry) {
        return qry != null && qry.getName() != null;
    }
}