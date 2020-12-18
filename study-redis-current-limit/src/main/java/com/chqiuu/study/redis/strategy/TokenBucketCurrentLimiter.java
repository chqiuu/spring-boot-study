package com.chqiuu.study.redis.strategy;

import com.chqiuu.study.redis.Constant;
import com.chqiuu.study.redis.enums.LimitTypeEnum;
import com.chqiuu.study.redis.exception.CurrentLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令桶算法限流实现
 *
 * @author chqiu
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = Constant.PREFIX, name = "algorithm", havingValue = "token", matchIfMissing = true)
public class TokenBucketCurrentLimiter implements CurrentLimiter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final DefaultRedisScript<Long> tokenBucketRedisScript;

    @Override
    public void execute(String key, LimitTypeEnum limitTypeEnum, long limit, long interval, long step) {
        //  命令桶算法限流实现
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(String.valueOf(limit));
        keyList.add(String.valueOf(step));
        keyList.add(String.valueOf(interval));
        keyList.add(String.valueOf(System.currentTimeMillis() / 1000));
        Long executeTimes = redisTemplate.execute(tokenBucketRedisScript, keyList, keyList);
        if (executeTimes != null) {
            if (executeTimes <= 0) {
                log.error("【{}】在单位时间 {} 秒内已达到访问上限，当前接口上限 {}", key, interval, limit);
                throw new CurrentLimitException("在单位时间已达到访问上限，请稍后再试");
            } else {
                log.info("【{}】在单位时间 {} 秒内还可访问 {} 次", key, interval, executeTimes - 1);
            }
        } else {
            log.error("其他错误：【{}】在单位时间 {} 秒内已达到访问上限，当前接口上限 {}", key, interval, limit);
            throw new CurrentLimitException("限流服务器错误，请稍后再试");
        }
    }
}
