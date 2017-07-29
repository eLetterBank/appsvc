package com.springdemo.shared.models;

public class ExecutionContext {
    private String requestId;
    private String sessionId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "httpHeader{" +
                "requestId=" + requestId +
                ", sessionId=" + sessionId +
                '}';
    }
}
