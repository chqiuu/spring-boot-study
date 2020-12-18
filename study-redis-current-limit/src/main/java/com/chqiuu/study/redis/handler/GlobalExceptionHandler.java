package com.chqiuu.study.redis.handler;

import com.chqiuu.study.redis.exception.CurrentLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author chqiu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CurrentLimitException.class)
    public String handleCurrentLimitException(CurrentLimitException e) {
        return e.getMessage();
    }
}
