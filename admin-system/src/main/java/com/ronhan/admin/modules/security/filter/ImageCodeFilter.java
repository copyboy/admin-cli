package com.ronhan.admin.modules.security.filter;

import cn.hutool.core.util.StrUtil;
import com.ronhan.admin.common.constant.AdminConstant;
import com.ronhan.admin.common.exception.ValidateCodeException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证码过滤器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 11:54
 */
@Component
public class ImageCodeFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    private ValueOperations<String, String> valueOperations;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1) 获取token参数
        String token = obtainToken(request);
        // 2) POST 方法,登陆接口, token不为空
        if (StrUtil.equals("/login", request.getRequestURI())
                && StrUtil.isEmpty(token)
                && StrUtil.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name())) {
            try {
                // 进行验证码的校验
                validateCode(request);
            } catch (ValidateCodeException ex) {
                // 3. 校验不通过，调用SpringSecurity的校验失败处理器
                authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) {
        String captcha = obtainImageCode(request);
        String key = obtainKey(request);
        // 验证验证码
        if (StrUtil.isBlank(captcha)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        // 从redis中获取之前保存的验证码跟前台传来的验证码进行匹配
        String redisCaptcha = valueOperations.get(AdminConstant.ADMIN_IMAGE_KEY + key);
        if (redisCaptcha == null) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!captcha.toLowerCase().equals(redisCaptcha)) {
            throw new ValidateCodeException("验证码错误");
        }
    }

    /**
     * 获取前端传递的图片验证码值
     */
    private String obtainImageCode(HttpServletRequest request) {
        return request.getParameter("code");
    }

    /**
     * 获取前端传来的图片验证码 KEY
     */
    private String obtainKey(HttpServletRequest request) {
        return request.getParameter("key");
    }

    /**
     * 获取前端传递的 TOKEN
     */
    private String obtainToken(HttpServletRequest request) {
        return request.getParameter("token");
    }
}
