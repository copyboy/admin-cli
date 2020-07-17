package com.ronhan.admin.log.util;

import com.ronhan.admin.log.annotation.SysOperaLog;
import org.aspectj.lang.JoinPoint;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * 日志工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 16:27
 */
public class LogUtil {

    /**
     * 获取系统日志操作注解的描述信息
     */
    public static String getControllerMethodDescription(JoinPoint point) throws Exception {

        // 获取连接点目标类名
        String targetName = point.getTarget().getClass().getName();
        // 获取连接点签名的方法名
        String methodName = point.getSignature().getName();
        // 获取连接点参数
        Object[] args = point.getArgs();
        // 获取连接点类的名称
        Class<?> clazz = Class.forName(targetName);
        // 获取类中方法
        Method[] methods = clazz.getMethods();
        String description = "";

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] classes = method.getParameterTypes();
                if (classes.length == args.length) {
                    description = method.getAnnotation(SysOperaLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try(PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

}
