package com.chqiuu.study.swagger.export;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chqiu
 * @date: 2019年7月9日 15:32:04
 */
@Slf4j
@SpringBootApplication
public class Swagger2ExportApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Swagger2ExportApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = null == env.getProperty("server.port") || "80".equals(env.getProperty("server.port")) ? "" : ":" + env.getProperty("server.port");
        String path = null == env.getProperty("server.servlet.context-path") ? "" : env.getProperty("server.servlet.context-path");
        String portPath = port + path;
        String delimiter = String.format("%100s", "").replaceAll("\\s", "=");
        log.info("\n{}\n【{}】项目已启动完成\n访问地址:\n\t" +
                        "Local: \t\thttp://localhost{}\n\t" +
                        "External: \thttp://{}{}\n\t" +
                        "Api: \t\thttp://localhost{}/swagger/doc.html\n{}"
                , delimiter
                , env.getProperty("spring.application.name"), portPath, ip, portPath, portPath
                , delimiter);
    }
}
