package com.chqiuu.study.controller.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest
public class StudyControllerTestWebMvcTest {
    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void contextLoads() {
        mockMvc.perform(MockMvcRequestBuilders.get("/foo/user")
                .param("name", "felord.cn")
                .param("age", "18")
                .header("Api-Version", "v1")).andDo(MockMvcResultHandlers.print());
    }
}
