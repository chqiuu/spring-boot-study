package com.chqiuu.study.redis.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.chqiuu.study.redis.Constant;
import com.chqiuu.study.redis.annotation.CurrentLimit;
import com.chqiuu.study.redis.enums.LimitTypeEnum;
import com.chqiuu.study.redis.exception.CurrentLimitException;
import com.chqiuu.study.redis.strategy.CurrentLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流切面实现
 *
 * @author chqiu
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CurrentLimitAspect {

    private final CurrentLimiter currentLimiter;

    /**
     * 将函数标记为切入点
     *
     * @param currentLimit 注解
     */
    @Pointcut("@annotation(currentLimit)")
    public void annotationPointcut(CurrentLimit currentLimit) {
    }

    /**
     * 将函数标记为在切入点覆盖的方法之前执行的通知
     *
     * @param joinPoint    切点
     * @param currentLimit 注解
     */
    @Before("annotationPointcut(currentLimit)")
    public void doBefore(JoinPoint joinPoint, CurrentLimit currentLimit) {
        String key = getRateKey(joinPoint, currentLimit.limitType());
        currentLimiter.execute(key, currentLimit.limitType(), currentLimit.limit(), currentLimit.interval(), currentLimit.step());
    }

    /**
     * 获取唯一标识此次请求的key
     *
     * @param joinPoint 切点
     * @param limitType 限流类型枚举
     * @return key
     */
    private String getRateKey(JoinPoint joinPoint, LimitTypeEnum limitType) {
        StringBuilder key = new StringBuilder(Constant.HASH_TAG);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        switch (limitType) {
            case USER:
                //以用户信息作为key
                if (request.getUserPrincipal() != null) {
                    key.append(request.getUserPrincipal().getName());
                } else {
                    throw new CurrentLimitException("用户未登录，request.getUserPrincipal().getName()为空");
                }
                break;
            case IP:
                //以IP地址作为key
                key.append(ServletUtil.getClientIP(request, ""));
                break;
            case CUSTOM:
                //以自定义内容作为key
                if (request.getAttribute(Constant.CUSTOM) != null) {
                    key.append(request.getAttribute(Constant.CUSTOM).toString());
                } else {
                    throw new CurrentLimitException("没有找到自定义KEY，请检查request.getAttribute('current-limit-custom')是否设置");
                }
                break;
            default:
                // LimitTypeEnum.ALL
                // 以方法名加参数列表作为唯一标识方法的key
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                key.append(signature.getMethod().getName());
                Class[] parameterTypes = signature.getParameterTypes();
                for (Class clazz : parameterTypes) {
                    key.append(clazz.getName());
                }
                key.append(joinPoint.getTarget().getClass());
                break;
        }
        return key.toString();
    }
}
