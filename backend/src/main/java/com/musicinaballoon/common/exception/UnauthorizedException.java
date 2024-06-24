package com.musicinaballoon.common.exception;

public class UnauthorizedException extends CommonException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
