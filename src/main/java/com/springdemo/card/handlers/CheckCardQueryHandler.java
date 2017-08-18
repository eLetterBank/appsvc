package com.springdemo.card.handlers;

import com.springdemo.card.contracts.query.CheckCardQuery;
import com.springdemo.card.contracts.query.CheckCardQueryResult;
import com.vsolv.appframework.cqrs.query.QueryHandler;
import com.springdemo.exceptions.ReturnCodes;
import org.springframework.stereotype.Service;

@Service
public class CheckCardQueryHandler implements QueryHandler<CheckCardQuery,CheckCardQueryResult> {
    @Override
    public CheckCardQueryResult execute(CheckCardQuery cardCheckQuery) {
        String responseData = cardCheckQuery.getCardNumber() + "-> Checked!";
        return new CheckCardQueryResult(responseData, ReturnCodes.SUCCESS.getId());
    }
}
