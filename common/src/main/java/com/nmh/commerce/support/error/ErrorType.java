package com.nmh.commerce.support.error;

import lombok.Getter;

@Getter
public enum ErrorType {

    DEFAULT_ERROR(500, ErrorCode.E500, "알 수 없는 오류가 발생했습니다.");

    private final int status;
    private final ErrorCode code;
    private final String message;

    ErrorType(int status, ErrorCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
