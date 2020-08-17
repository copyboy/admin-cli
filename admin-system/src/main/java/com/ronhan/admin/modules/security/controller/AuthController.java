package com.ronhan.admin.modules.security.controller;

import com.ronhan.admin.common.constant.AdminConstant;
import com.ronhan.admin.common.utils.R;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登陆页面 验证方式
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-17 16:30
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private ValueOperations<String, String> valueOperations;

    /**
     * 生成验证码
     */
    @GetMapping("/captcha.jpg")
    public R captcha() {
        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();

        String key = UUID.randomUUID().toString();
        valueOperations.set(AdminConstant.ADMIN_IMAGE_KEY + key, result, 2, TimeUnit.MINUTES);
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("img", captcha.toBase64());
        return R.ok(map);
    }

}
