package com.springdemo.exceptions;

public class ServiceException extends Exception {
    private final int errorCode;
    private final String errorMsg;

    public ServiceException(ReturnCodes code) {
        this.errorMsg = code.getMessage();
        this.errorCode = code.getId();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
