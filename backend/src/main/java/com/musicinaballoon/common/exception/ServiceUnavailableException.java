package com.musicinaballoon.common.exception;

public class ServiceUnavailableException extends CommonException {

    public ServiceUnavailableException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ServiceUnavailableException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
