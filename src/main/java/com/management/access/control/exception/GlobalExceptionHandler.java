package com.management.access.control.exception;

import com.management.access.control.util.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.management.access.control.util.AccessConstant.ERROR_CODE_9002;
import static com.management.access.control.util.AccessConstant.ERROR_CODE_9003;


/**
 * global exception handler
 * @author ben
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleAccessException(AccessException e) {
        logger.error("got exception. {} . {}", e.getErrMsg(), getAllExceptionMsg(e));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Response.error(e.getErrCode(), e.getErrMsg()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleParameterError(IllegalArgumentException ex) {
        logger.error("got exception. {}. {}", ex.getMessage(), getAllExceptionMsg(ex));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.error(ERROR_CODE_9002, ex.getMessage()));
    }

    /**
     * Handle other exception.
     *
     * @param e other exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        logger.error("got exception. {}", getAllExceptionMsg(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error(ERROR_CODE_9003,e.getMessage()));
    }

    public static String getAllExceptionMsg(Throwable e) {
        Throwable cause = e;
        StringBuilder strBuilder = new StringBuilder();

        while (cause != null && !StringUtils.isEmpty(cause.getMessage())) {
            strBuilder.append("caused: ").append(cause.getMessage()).append(";");
            cause = cause.getCause();
        }

        return strBuilder.toString();
    }
}
