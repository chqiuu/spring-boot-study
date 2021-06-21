package com.chqiuu.study.controller.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Slf4j
@Component
public class ServletContextAwareImpl implements ServletContextAware {
    /**
     * 在填充普通bean属性之后但在初始化之前调用
     * 类似于InitializingBean's 的 afterPropertiesSet 或自定义init方法的回调
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        log.info("启动时自动执行 ServletContextAware 的 setServletContext 方法");
    }
}
