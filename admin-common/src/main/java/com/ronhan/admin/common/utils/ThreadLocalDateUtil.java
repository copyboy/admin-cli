package com.ronhan.admin.common.utils;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 本地线程 日期工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:31
 */
@UtilityClass
public class ThreadLocalDateUtil {

    public ThreadLocal<DateFormat> threadLocal =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

}
