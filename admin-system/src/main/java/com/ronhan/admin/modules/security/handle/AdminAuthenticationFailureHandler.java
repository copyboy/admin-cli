package com.ronhan.admin.modules.security.handle;

import com.ronhan.admin.common.exception.ValidateCodeException;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.security.util.SecurityUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败处理器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 11:25
 */
@Component
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {

        String message;
        if (e instanceof ValidateCodeException) {
            message = e.getMessage();
        } else {
            message = "认证失败，请联系网站管理员！";
        }

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        SecurityUtil.writeJavaScript(R.error(message), httpServletResponse);

    }
}
