package com.chqiuu.study.controller.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListenerTest {
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("启动时自动执行  @EventListener 注解方法");
    }
}
