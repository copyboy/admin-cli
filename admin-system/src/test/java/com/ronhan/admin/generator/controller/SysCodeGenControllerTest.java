package com.ronhan.admin.generator.controller;

import cn.hutool.json.JSONUtil;
import com.ronhan.admin.AdminApplication;
import com.ronhan.admin.generator.domain.CodeGenConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * 代码生成控制器 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 9:04
 */
@SpringBootTest(classes = {AdminApplication.class})
@Slf4j
class SysCodeGenControllerTest {

    MockMvc mvc;

    @Resource
    private WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @SuppressWarnings({"deprecation"})
    void getTableList() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/codegen/getTableList")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("tableSchema", "ronhan"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getTableColumnList() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("tableSchema","ronhan");
        params.add("tableName","sys_log");

        mvc.perform(MockMvcRequestBuilders
                .get("/codegen/getTableColumnList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @SuppressWarnings({"deprecation"})
    void generatorCode() throws Exception {
        CodeGenConfig config = new CodeGenConfig();
        config.setPackageName("com.ronhan.admin.modules.sys");
        config.setModuleName("admin-system");
        config.setAuthor("qingdong.zhang");
        config.setComments("TODO 求你写点注释吧");
        config.setTablePrefix("");
        config.setTableName("sys_user");
        mvc.perform(MockMvcRequestBuilders
                .post("/codegen/codegen")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(JSONUtil.toJsonStr(config)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}