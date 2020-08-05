package com.ronhan.admin.modules.security.handle;

import cn.hutool.http.HttpStatus;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问未授权资源
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 11:45
 */
@Slf4j
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        log.error("请求访问: [{}] 接口， 没有访问权限" , request.getRequestURI());
        SecurityUtil.writeJavaScript(R.error(HttpStatus.HTTP_FORBIDDEN,
                "请求访问: [" + request.getRequestURI() + "] 接口,没有访问权限"), response);
    }
}
