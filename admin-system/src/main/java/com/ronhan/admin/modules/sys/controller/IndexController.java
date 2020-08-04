package com.ronhan.admin.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.ronhan.admin.common.exception.ValidateCodeException;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页控制器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 14:53
 */
@RestController
public class IndexController {

    @Resource
    private ValueOperations<String, String> valueOperations;

    @Resource
    private ISysUserService userService;

    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO) {

        String redisCode = valueOperations.get(userDTO.getPhone());
        if (StrUtil.isBlank(redisCode)) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!userDTO.getSmsCode().toLowerCase().equals(redisCode)) {
            throw new ValidateCodeException("短信验证码错误");
        }
        return R.ok(userService.register(userDTO));
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    public R login(String username, String password, HttpServletRequest request) {
        // 社交快速登录
        String token = request.getParameter("token");
        if (StrUtil.isNotEmpty(token)) {
            return R.ok(token);
        }
        return R.ok(userService.login(username, password));
    }

    /**
     * 详情
     **/
    @GetMapping("/info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("admin");
        map.put("roles", list);
        map.put("name", "Super Admin");
        return R.ok(map);
    }

    /**
     * 退出
     **/
    @RequestMapping("/logout")
    public String logout() {
        return "success";
    }


    /**
     * 权限访问测试 接口
     **/
    @GetMapping("/test/info")
    @PreAuthorize(value = "hasRole('TEST_INFO')")
    public R testInfo() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("TEST_INFO");
        map.put("roles", list);
        map.put("name", "TEST USER");
        return R.ok(map);
    }
}
