package com.springdemo.exceptions;

public enum ReturnCodes {
    SUCCESS(0, "Success"),
    INVALID_REQUEST(99999, "The request is invalid"),
    MISSING_QUERY_PARAMETER(10100, "Required query parameter is missing"),
    MISSING_COMMAND_PARAMETER(10200, "Required query parameter is missing");

    private final int id;
    private final String message;

    ReturnCodes(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }
}