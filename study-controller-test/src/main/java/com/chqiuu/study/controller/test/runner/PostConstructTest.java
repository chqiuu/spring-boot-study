package com.chqiuu.study.controller.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class PostConstructTest {
    @PostConstruct
    public void postConstruct() {
        log.info("启动时自动执行  @PostConstruct 注解方法");
    }
}
