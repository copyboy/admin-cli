package com.ronhan.admin.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * IP 工具 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:34
 */
class IPUtilTest {

    @Test
    void getCityInfo() {

        String retMsg = IPUtil.getCityInfo("27.16.148.184");
        String expect = "中国|0|湖北省|武汉市|电信";
        Assertions.assertEquals(expect, retMsg);
    }
}