package com.ronhan.admin.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Admin 基础异常
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 16:50
 */
public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg,Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public BaseException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
