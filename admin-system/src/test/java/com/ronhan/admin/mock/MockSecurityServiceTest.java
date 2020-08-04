package com.ronhan.admin.mock;

import com.ronhan.admin.AdminApplication;
import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring Security 服务 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 10:19
 */
@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class MockSecurityServiceTest {

    @Resource
    private MockMvc mockMvc;


    @Test
    @SneakyThrows
    void givenNonUser_whenTestInfo_thenCode401() {
        Matcher<String> matcher = MatchesPattern.matchesPattern("请求访问:/test/info接口,经jwt 认证失败,无法访问系统资源");
        mockMvc.perform(get("/test/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isNumber())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.msg").value(matcher))
        ;
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "TEST_ERR")
    void givenErrorRoleUser_whenTestInfo_thenCode403() {

        Matcher<String> matcher = MatchesPattern.matchesPattern("请求访问: [/test/info] 接口， 没有访问权限");
        mockMvc.perform(get("/test/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isNumber())
                .andExpect(jsonPath("$.code").value(403))
                .andExpect(jsonPath("$.data.name").value(matcher))
        ;

    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "TEST_INFO")
    void givenMockUser_whenTestInfo_thenCode200() {
        mockMvc.perform(get("/test/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isNumber())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("TEST USER"))
        ;

    }
}
