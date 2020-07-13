package com.ronhan.admin.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bean 工具测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 18:40
 */
class BeanUtilTest {

    @Test
    void map2Bean() throws Exception {

        Map<String, Object> beanMap = new HashMap<>();
        beanMap.put("code", 500);
        beanMap.put("msg", "服务器异常");

        R r = BeanUtil.map2Bean(beanMap, R.class);

        R expect = new R();
        expect.setCode(500);
        expect.setMsg("服务器异常");

        Assertions.assertEquals(expect.toString(), r.toString());
    }
}