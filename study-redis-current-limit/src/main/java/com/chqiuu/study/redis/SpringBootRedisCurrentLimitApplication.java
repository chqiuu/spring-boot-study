package com.chqiuu.study.redis;

import cn.hutool.core.util.StrUtil;
import com.chqiuu.study.redis.annotation.EnableCurrentLimit;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 基于Redis网站限流实现
 *
 * @author chqiu
 */
@Slf4j
@EnableCurrentLimit
@SpringBootApplication
public class SpringBootRedisCurrentLimitApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(SpringBootRedisCurrentLimitApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = "80".equals(env.getProperty("server.port")) ? "" : (":" + env.getProperty("server.port"));
        String path = StrUtil.isBlank(env.getProperty("server.servlet.context-path")) ? "" : env.getProperty("server.servlet.context-path");
        String applicationName = env.getProperty("spring.application.name");
        log.info("\n----------------------------------------------------------\n\t" +
                applicationName + "，访问地址:\n\t" +
                "Local: \t\thttp://localhost" + port + path + "\n\t" +
                "External: \thttp://" + ip + port + path + "\n\t" +
                "----------------------------------------------------------");
    }
}
