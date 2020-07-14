package com.ronhan.admin.log.annotation;

import java.lang.annotation.*;

/**
 * 操作日志 注解类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysOperaLog {

    String description() default "";
}
