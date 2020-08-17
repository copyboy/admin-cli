package com.ronhan.admin.generator.controller;

import com.ronhan.admin.AdminApplication;
import com.ronhan.admin.common.exception.ValidateCodeException;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.controller.IndexController;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 首页控制器 测试类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-07 9:42
 */
@SpringBootTest(classes = AdminApplication.class)
public class IndexControllerTest {

    @Resource
    private IndexController controller;

    @Test
    void should_return_validate_code_given_non_phone_when_post_register() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("qingdong.zhang").setPassword("123456");
        try {
            R response = controller.register(userDTO);
        } catch (Exception e) {
            // 这里是控制器层报错，还未被全局异常拦截器处理
            assert e instanceof IllegalArgumentException;
        }
    }

    @Test
    void should_return_validate_code_given_phone_have_not_sms_when_post_register() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("qingdong.zhang").setPassword("123456").setPhone("13003080730");
        try {
            controller.register(userDTO);
        } catch (Exception e) {
            // 这里是控制器层报错，还未被全局异常拦截器处理
            assert e instanceof ValidateCodeException;
            Assert.assertEquals("验证码已失效", e.getMessage());
        }
    }
}
