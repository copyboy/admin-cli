package com.ronhan.admin.common.exception;

import com.ronhan.admin.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 自定义异常处理器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 16:57
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public R handlerException(BaseException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNotFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handlerDuplicateKeyException(DuplicateKeyException e){
        log.error(e.getMessage(), e);
        return R.error(300, "数据库中已存在该记录");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handlerAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return R.error(403, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public R handlerAccountExpireException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler
    public R handlerUserNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public R handlerSqlException(SQLException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(ValidateCodeException.class)
    public R handlerValidateCodeException(ValidateCodeException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }
}
