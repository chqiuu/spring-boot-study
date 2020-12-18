package com.chqiuu.study.redis.controller;

import com.chqiuu.study.redis.Constant;
import com.chqiuu.study.redis.annotation.CurrentLimit;
import com.chqiuu.study.redis.enums.LimitTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chqiu
 */
@RestController
public class LimiterController {
    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();

    @CurrentLimit(interval = 10, limit = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {
        return ATOMIC_INTEGER_1.incrementAndGet();
    }

    @CurrentLimit(interval = 10, limit = 3, limitType = LimitTypeEnum.CUSTOM)
    @GetMapping("/limitTest2")
    public int testLimiter2(HttpServletRequest request) {
        //根据一系列操作查出来了用户id
        //限流时在httpServletRequest中根据Const.CUSTOM的值进行限流
        request.setAttribute(Constant.CUSTOM, "用户id");
        return ATOMIC_INTEGER_2.incrementAndGet();
    }

    @CurrentLimit(interval = 10, limit = 3, limitType = LimitTypeEnum.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {
        return ATOMIC_INTEGER_3.incrementAndGet();
    }
}
