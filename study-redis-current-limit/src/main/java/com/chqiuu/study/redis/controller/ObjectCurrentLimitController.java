package com.chqiuu.study.redis.controller;

import com.chqiuu.study.redis.annotation.CurrentLimit;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CurrentLimit(interval = 30, limit = 10, message = "class，您的手速太快了，请稍后再试")
@RestController
@RequestMapping("/object")
public class ObjectCurrentLimitController {

    @PostMapping("/havaParam")
    public void havaParam(@RequestBody Map<String, String> map) {
        System.out.println("业务代码havaParam……");
    }

    @GetMapping("/noParam")
    public void noParam() {
        System.out.println("业务代码noParam……");
    }
}