package com.musicinaballoon.common;

import com.musicinaballoon.common.exception.BadRequestException;
import com.musicinaballoon.common.exception.CommonException;
import com.musicinaballoon.common.exception.ErrorCode;
import com.musicinaballoon.common.exception.NotFoundException;
import com.musicinaballoon.common.exception.ServiceUnavailableException;
import com.musicinaballoon.common.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger("ErrorLogger");
    private static final String LOG_FORMAT_INFO = "\n[🔵INFO] - ({} {})\n{}\n {}: {}";
    private static final String LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {})";
    private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})";
    // INFO
    /*
        [🔵INFO] - (POST /info)
        ERROR_CODE_NAME
         com.musicinaballoon.common.exception.BadRequestException: 테스트용 에러입니다.
     */

    // WARN
    /*
        [🟠WARN] - (POST /warn)
        ERROR_CODE_NAME
         com.musicinaballoon.common.exception.InternalServerException: 테스트용 에러입니다.
          at com.musicinaballoon.user.presentation.UserController.getWarn(UserController.java:129)
     */

    // ERROR
    /*
        [🔴ERROR] - (POST /error)
         com.musicinaballoon.common.exception.InternalServerException: 테스트용 에러입니다.
          at com.musicinaballoon.user.presentation.UserController.getWarn(UserController.java:129)
     */

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handle(BadRequestException e, HttpServletRequest request) {
        logInfo(e, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handle(UnauthorizedException e, HttpServletRequest request) {
        logInfo(e, request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoundException e, HttpServletRequest request) {
        logInfo(e, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handle(ServiceUnavailableException e, HttpServletRequest request) {
        logWarn(e, request);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e, HttpServletRequest request) {
        logError(e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    private void logInfo(CommonException e, HttpServletRequest request) {
        log.info(LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(),
                e.getErrorCode(), e.getClass().getName(), e.getMessage());
    }

    private void logWarn(Exception e, HttpServletRequest request) {
        log.warn(LOG_FORMAT_WARN, request.getMethod(), request.getRequestURI(), e);
    }

    private void logError(Exception e, HttpServletRequest request) {
        log.error(LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), e);
    }

    record ErrorResponse(ErrorCode errorCode, String message) {

        static ErrorResponse from(CommonException e) {
            return new ErrorResponse(e.getErrorCode(), e.getMessage());
        }

        static ErrorResponse from(ErrorCode errorCode) {
            return new ErrorResponse(errorCode, errorCode.getMessage());
        }
    }
}
