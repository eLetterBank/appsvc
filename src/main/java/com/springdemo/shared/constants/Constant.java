package com.springdemo.shared.constants;

public final class Constant {
    Constant() {
        throw new IllegalStateException("Constant class");
    }

    //HTTP header values
    public static final String X_VSOLV_NONCE = "x-vsolv-nonce";
    public static final String X_VSOLV_SIGNATURE = "x-vsolv-signature";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    //Custom HTTP request attributes
    public static final String AUDITINFO = "AUDITINFO";
    public static final String STARTTIME = "STARTTIME";
    public static final String ENDTIME = "ENDTIME";
}
