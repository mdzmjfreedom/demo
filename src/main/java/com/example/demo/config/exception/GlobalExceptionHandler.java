package com.example.demo.config.exception;

import com.alibaba.cola.exception.BizException;
import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handleGlobalException(Exception ex) {
        return Result.error(ex.getMessage(), "-1");
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleBizException(BizException ex) {
        return Result.error(ex.getMessage(), ex.getErrCode());
    }
}
