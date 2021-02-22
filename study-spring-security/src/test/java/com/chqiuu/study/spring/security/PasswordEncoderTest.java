package com.chqiuu.study.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class PasswordEncoderTest {

    @Test
    void BCryptPasswordEncoderTest() {
        String rawPassword = "asdfaerqwwer";
        for (int i = 0; i < 200; i++) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
            long startTime = System.currentTimeMillis();
            log.info("开始：{}", i);
            String m = passwordEncoder.encode(rawPassword);
            long endTime = System.currentTimeMillis();
            log.info("耗时：{}；密码 {}", endTime - startTime, m);
        }
    }
}
