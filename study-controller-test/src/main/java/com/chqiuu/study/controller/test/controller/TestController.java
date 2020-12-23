package com.chqiuu.study.controller.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();

    @GetMapping("/test1")
    public String test() {
        return String.format("第%s次访问！", ATOMIC_INTEGER_1.incrementAndGet());
    }
}
