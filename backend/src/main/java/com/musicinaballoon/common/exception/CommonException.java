package com.musicinaballoon.common.exception;

import org.springframework.core.NestedRuntimeException;

public abstract class CommonException extends NestedRuntimeException {

    private final ErrorCode errorCode;

    protected CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    protected CommonException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    protected CommonException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
