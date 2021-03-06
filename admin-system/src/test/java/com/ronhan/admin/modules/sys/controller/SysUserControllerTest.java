package com.ronhan.admin.modules.sys.controller;

import cn.hutool.json.JSONUtil;
import com.ronhan.admin.AdminApplication;
import com.ronhan.admin.constant.TestAccount;
import com.ronhan.admin.modules.security.util.JwtUtil;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import com.ronhan.admin.security.LoginType;
import com.ronhan.admin.security.SecurityUser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private String token;

    @BeforeEach
    void setUp() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("sys:user:view"));
        authorities.add(new SimpleGrantedAuthority("sys:user:add"));
        // 加入 ROLE_5 原因: DataScopeInterceptor 在拦截 DataScope参数时,会查询该角色的数据域
        authorities.add(new SimpleGrantedAuthority(TestAccount.roleId));
        SecurityUser userDetails = new SecurityUser(TestAccount.userId, TestAccount.username,
                TestAccount.password, authorities, LoginType.normal);
        token = JwtUtil.generateToken(userDetails);
    }

    @Test
    @SneakyThrows
//    @WithMockUser(authorities = {"sys:user:view", "ROLE_5"}) // 失败原因：User cannot be cast to SecurityUser
    void should_return_all_user_given_jwt_when_get_user() {
        mockMvc.perform(get("/user?current=1&size=10&deptId=&username=")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @SneakyThrows
    @Transactional
    void should_add_one_account_given_new_account_when_post_user() {
        UserDTO user = new UserDTO();
        user.setUsername("TEST_4_ALL").setPhone("13003080731").setEmail("2799665839@qq.com").setDeptId(6).setLockFlag("0");
        List<Integer> roleList = new ArrayList<>(Arrays.asList(5, 7, 8));
        user.setRoleList(roleList);
        mockMvc.perform(post("/user")
                .header("Authorization", "Bearer " + token)
                .content(JSONUtil.toJsonStr(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andDo(mvcResult -> mvcResult.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }
}