package com.springdemo.exceptions;

public enum ReturnCodes {
    SUCCESS(0, "Success"),
    INVALID_REQUEST(99999, "The request is invalid"),
    MISSING_HEADER_VALUE(10101, "Required headers not specified in the request"),
    INTERNAL_SERVER_ERROR(10103, "Internal server error"),
    MISSING_QUERY_PARAMETER(10201, "Required query parameter is missing"),
    MISSING_COMMAND_PARAMETER(10301, "Required query parameter is missing"),
    MISSING_EXECUTION_CONTEXT(10303, "Required execustion context is missing"),
    MISSING_AUDIT_EVENT_CONTEXT(10305, "Required audit event context is missing"),
    MISSING_AUDIT_LOGGER(10307, "Required audit logger is missing");


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