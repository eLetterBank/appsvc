package com.springdemo.greeting.handlers;

import com.springdemo.greeting.contracts.queries.GreetingQuery;
import com.springdemo.greeting.contracts.queries.GreetingQueryResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class GreetingService implements QueryHandler<GreetingQuery, GreetingQueryResult> {

    @Autowired
    private TimeRepository timeRepo;

    @Override
    public GreetingQueryResult execute(GreetingQuery qry) {
        String responseData = "Hello " + qry.getName() + " "
                + qry.getAddress().getStreet() + "! - "
                + timeRepo.getServerTime().toString();

        return new GreetingQueryResult(responseData);
    }
}