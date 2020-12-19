package com.chqiuu.study.redis.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.chqiuu.study.redis.Constant;
import com.chqiuu.study.redis.annotation.CurrentLimit;
import com.chqiuu.study.redis.enums.LimitTypeEnum;
import com.chqiuu.study.redis.exception.CurrentLimitException;
import com.chqiuu.study.redis.strategy.BaseCurrentLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流切面实现（面向方法）
 *
 * @author chqiu
 */
@Order(2)
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class MethodCurrentLimitAspect {

    private final BaseCurrentLimiter currentLimiter;

    /**
     * 将方法标记为切入点
     *
     * @param currentLimit 注解
     */
    @Pointcut("@annotation(currentLimit)")
    public void annotationPointcut(CurrentLimit currentLimit) {
    }

    /**
     * 将方法标记为在切入点覆盖的方法之前执行的通知
     *
     * @param joinPoint    切点
     * @param currentLimit 注解
     */
    @Before(value = "annotationPointcut(currentLimit)", argNames = "joinPoint,currentLimit")
    public void doAnnotationBefore(JoinPoint joinPoint, CurrentLimit currentLimit) {
        boolean isAllowAccess = currentLimiter.check(joinPoint, true, currentLimit.key(), currentLimit.limitType(), currentLimit.limit(), currentLimit.interval(), currentLimit.step());
        if (!isAllowAccess) {
            throw new CurrentLimitException(currentLimit.message());
        }
    }
}
