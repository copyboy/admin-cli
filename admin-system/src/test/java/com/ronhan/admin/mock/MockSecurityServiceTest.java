package com.ronhan.admin.mock;

import com.ronhan.admin.AdminApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void unauthorized_when_without_security_config() {
        mockMvc.perform(get("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "admin", roles = "ADMIN")
    void authorized_when_config_role() {
        mockMvc.perform(get("/info")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andExpect(status().isOk())
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print());
    }
}
