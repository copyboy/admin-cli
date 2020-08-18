package com.ronhan.admin.modules.sys.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.AdminApplication;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.domain.SysUser;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 系统用户控制器 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-18 17:02
 */
@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
class SysUserControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(authorities = "sys:user:view", username = "admin", password = "123456")
    void getList() {
        mockMvc.perform(get("/user?current=1&size=10&deptId=&username=")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

    }
}