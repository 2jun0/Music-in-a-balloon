package com.musicinaballoon.common.exception;

public class BadRequestException extends CommonException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
