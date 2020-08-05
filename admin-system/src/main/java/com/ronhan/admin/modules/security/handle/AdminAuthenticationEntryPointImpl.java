package com.ronhan.admin.modules.security.handle;

import cn.hutool.http.HttpStatus;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 解决认证过的用户,访问无权限资源时的异常
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 11:42
 */
@Slf4j
public class AdminAuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -2892771012150224188L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        log.error("请求访问: [{}] 接口， 经jwt认证失败，无法访问系统资源.", request.getRequestURI());
        SecurityUtil.writeJavaScript(R.error(HttpStatus.HTTP_UNAUTHORIZED,
                "请求访问: [" + request.getRequestURI() + "] 接口,经JWT认证失败,无法访问系统资源."), response);

    }
}
