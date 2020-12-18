package com.chqiuu.study.redis.strategy;

import com.chqiuu.study.redis.enums.LimitTypeEnum;

/**
 * 限流器算法策略接口
 *
 * @author chqiu
 */
public interface CurrentLimiter {

    /**
     * 限流器算法
     *
     * @param key           接口
     * @param limitTypeEnum 限流方式
     * @param limit         允许访问多少次
     * @param interval      间隔时间
     * @param step          递减步长
     */
    void execute(String key, LimitTypeEnum limitTypeEnum, long limit, long interval, long step);
}
