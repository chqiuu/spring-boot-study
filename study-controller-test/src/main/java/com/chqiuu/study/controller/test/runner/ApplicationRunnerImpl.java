package com.chqiuu.study.controller.test.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    /**
     * 用于指示bean包含在SpringApplication中时应运行的接口。可以定义多个ApplicationRunner bean
     * 在同一应用程序上下文中，可以使用有序接口或@order注释对其进行排序。
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动时自动执行 ApplicationRunner 的 run 方法");

        Set<String> optionNames = args.getOptionNames();
        for (String optionName : optionNames) {
            log.info("这是传过来的参数[{}]", optionName);
        }
        String[] sourceArgs = args.getSourceArgs();
        for (String sourceArg : sourceArgs) {
            log.info("这是传过来sourceArgs[{}]", sourceArg);
        }
    }
}
