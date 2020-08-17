package com.ronhan.admin.mock;

import cn.hutool.json.JSONUtil;
import com.ronhan.admin.AdminApplication;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 首页模拟测试
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-03 9:56
 */
@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
public class MockIndexControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void postNewUserThenReturnWithId() {
        UserDTO user = new UserDTO();
        user.setUsername("qingdong.zhang").setPassword("123456");
        String response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJsonStr(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").isNumber())
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(JSONUtil.toBean(response, UserDTO.class), user.setUserId(1));
    }
}
