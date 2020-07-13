package com.ronhan.admin.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 16:43
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class R implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code = 200;

    private String msg;

    private Object data;

    public static R ok() {
        R r = new R();
        r.setMsg("操作成功");
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    public static R error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
