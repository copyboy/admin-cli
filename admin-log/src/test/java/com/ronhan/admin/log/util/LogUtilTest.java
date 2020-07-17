package com.ronhan.admin.log.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 日志工具 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 16:35
 */
class LogUtilTest {

    @Test
    void getStackTrace() {

        try {
            throw new RuntimeException("initial error");
        } catch (Exception e) {
            String ret = LogUtil.getStackTrace(e);
//            System.out.println(ret);
            Assertions.assertNotNull(ret);
        }

    }
}