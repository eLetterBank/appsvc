package com.springdemo.greeting.handlers;

import com.springdemo.greeting.contracts.GreetingQuery;
import com.springdemo.greeting.contracts.GreetingQueryResult;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
class GreetingService implements QueryHandler<GreetingQuery, GreetingQueryResult> {
    public GreetingQueryResult execute(GreetingQuery qry) {

        String timeStamp = Calendar.getInstance().getTime().toString();
        String responseData = "Hello " + qry.getName() + " " + qry.getAddress().getStreet() + "! - " + timeStamp;

        return new GreetingQueryResult(responseData);
    }
}