package com.springdemo.greeting.handlers;

import com.springdemo.greeting.contracts.GreetingQuery;
import com.springdemo.greeting.contracts.GreetingQueryResult;
import com.springdemo.greeting.repositories.TimeRepository;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class GreetingService implements QueryHandler<GreetingQuery, GreetingQueryResult> {

    @Autowired
    private TimeRepository timeRepo;

    public GreetingQueryResult execute(GreetingQuery qry) {
        String timeStamp = timeRepo.getServerTime();
        String responseData = "Hello " + qry.getName() + " " + qry.getAddress().getStreet() + "! - " + timeStamp;

        return new GreetingQueryResult(responseData);
    }
}