package com.musicinaballoon.common.exception;

public class InternalException extends CommonException {

    public InternalException(ErrorCode errorCode) {
        super(errorCode);
    }
}
