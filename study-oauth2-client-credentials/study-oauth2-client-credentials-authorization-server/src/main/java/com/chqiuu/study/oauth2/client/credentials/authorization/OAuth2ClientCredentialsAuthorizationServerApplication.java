package com.chqiuu.study.oauth2.client.credentials.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chqiu
 */
@Slf4j
@SpringBootApplication
public class OAuth2ClientCredentialsAuthorizationServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(OAuth2ClientCredentialsAuthorizationServerApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = null == env.getProperty("server.port") || "80".equals(env.getProperty("server.port")) ? "" : ":" + env.getProperty("server.port");
        String path = null == env.getProperty("server.servlet.context-path") ? "" : env.getProperty("server.servlet.context-path");
        String portPath = port + path;
        String delimiter = String.format("%100s", "").replaceAll("\\s", "=");
        log.info("\n{}\n【{}】项目已启动完成\n访问地址:" +
                        "\n\tLocal: \t\thttp://localhost{}" +
                        "\n\tExternal: \thttp://{}{}" +
                        "\n{}"
                , delimiter
                , null == env.getProperty("spring.application.name") ? "" : env.getProperty("spring.application.name"), portPath, ip, portPath
                , delimiter);
    }
}