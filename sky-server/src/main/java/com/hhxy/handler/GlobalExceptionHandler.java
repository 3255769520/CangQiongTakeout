package com.hhxy.handler;

import com.hhxy.constant.MessageConstant;
import com.hhxy.exception.BaseException;
import com.hhxy.exception.SetmealEnableFailedException;
import com.hhxy.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("违反约束异常：{}", message);

        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    @ExceptionHandler(SetmealEnableFailedException.class)
    public Result<String> exceptionHandler(SetmealEnableFailedException ex) {
        log.error("业务异常：{}", ex.getMessage());
        return Result.success(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("业务异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
