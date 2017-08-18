package com.springdemo.card.contracts.query;

import com.vsolv.appframework.cqrs.query.Query;

public class CheckCardQuery implements Query {
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
