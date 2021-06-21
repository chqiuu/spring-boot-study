package com.chqiuu.study.controller.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Component
public class ServletContextListenerImpl implements ServletContextListener {

    /**
     * 静态代码块会在依赖注入后自动执行,并优先执行
     */
    static{
        log.info("启动时自动执行 静态代码块");
    }

    /**
     * 在初始化Web应用程序中的任何过滤器或Servlet之前，将通知所有ServletContextListener上下文初始化。
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("启动时自动执行 ServletContextListener 的 contextInitialized 方法");
    }
}
