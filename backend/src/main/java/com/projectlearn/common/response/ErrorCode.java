package com.projectlearn.common.response;

public enum ErrorCode {
    BAD_REQUEST(400),
    BUSINESS_ERROR(4001),
    INTERNAL_ERROR(500);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
