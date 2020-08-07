package com.ronhan.admin.security.util;

import com.alibaba.fastjson.JSON;
import com.ronhan.admin.common.exception.BaseException;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.security.SecurityUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 安全服务工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 14:35
 */
@UtilityClass
@Slf4j
public class SecurityUtil {


    public void writeJavaScript(R r, HttpServletResponse response) throws IOException {

        response.setStatus(200);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(r));
        printWriter.flush();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     * @return Security 框架返回的用户
     */
    public SecurityUser getUser() {

        try {
            return (SecurityUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            log.error("登录状态过期 Unauthorized !");
            throw new BaseException("登录状态过期", HttpStatus.UNAUTHORIZED.value());
        }
    }


}
