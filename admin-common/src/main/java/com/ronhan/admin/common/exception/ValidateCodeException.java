package com.ronhan.admin.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 16:54
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 80965318001568745L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
