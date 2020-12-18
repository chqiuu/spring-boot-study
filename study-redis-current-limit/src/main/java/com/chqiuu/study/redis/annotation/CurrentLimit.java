package com.chqiuu.study.redis.annotation;

import com.chqiuu.study.redis.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * 限流注解（用方法）
 *
 * @author chqiu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CurrentLimit {
    /**
     * 设置限流条件唯一标识
     *
     * @return 限流条件唯一标识
     */
    String key() default "";

    /**
     * 设置给定的时间范围 单位(秒)
     *
     * @return 给定的时间范围 单位(秒)。默认值60
     */
    long interval() default 60;

    /**
     * 设置单位时间内可访问次数（限流次数）
     * 向令牌桶中添加数据的时间间隔, 以秒为单位。默认值10秒
     *
     * @return 单位时间内可访问次数（限流次数）。默认值10
     */
    long limit() default 10;

    /**
     * 设置每次为令牌桶中添加的令牌数量
     *
     * @return 每次为令牌桶中添加的令牌数量。默认值5个
     */
    long step() default 5;

    /**
     * 设置限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    LimitTypeEnum limitType() default LimitTypeEnum.ALL;

}
