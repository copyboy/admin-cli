package com.ronhan.admin.log.aspect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ronhan.admin.common.utils.IPUtil;
import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.domain.SysLog;
import com.ronhan.admin.log.event.SysLogEvent;
import com.ronhan.admin.log.util.LogUtil;
import com.ronhan.admin.security.SecurityUser;
import com.ronhan.admin.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * 系统日志切面
 * ①切面注解得到请求数据 -> ②发布监听事件 -> ③异步监听日志入库
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:49
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    private final ThreadLocal<SysLog> sysLogThreadLocal = new ThreadLocal<>();

    private static final int NORMAL = 1;
    private static final int ABNORMAL = 2;

    /**
     * 事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布
     **/
    @Resource
    private ApplicationContext applicationContext;

    /***
     * 定义controller切入点拦截规则，拦截SysLog注解的方法
     */
    @Pointcut("@annotation(com.ronhan.admin.log.annotation.SysOperaLog)")
    public void sysLogAspect(){

    }

    @Before(value = "sysLogAspect()")
    public void recordLog(JoinPoint joinPoint) throws Throwable {

        SysLog sysLog = new SysLog();
        sysLogThreadLocal.set(sysLog);

        long beginTime = Instant.now().toEpochMilli();

        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

        SecurityUser securityUser = SecurityUtil.getUser();
        sysLog.setUserName(securityUser.getUsername());
        sysLog.setActionUrl(URLUtil.getPath(request.getRequestURI()));
        sysLog.setStartTime(LocalDateTime.now());
        sysLog.setFinishTime(LocalDateTime.now());
        String ip = ServletUtil.getClientIP(request);
        sysLog.setIp(ip);
        sysLog.setLocation(IPUtil.getCityInfo(ip));
        sysLog.setRequestMethod(request.getMethod());
        String uaStr = request.getHeader(Header.USER_AGENT.name());
        UserAgent ua = UserAgentUtil.parse(uaStr);
        sysLog.setBrowser(ua.getBrowser().toString());
        sysLog.setOs(ua.getOs().toString());


        // 访问方法
        sysLog.setActionMethod(joinPoint.getSignature().getName());
        // 访问类名
        sysLog.setClassPath(joinPoint.getTarget().getClass().getName());
        // 携带参数
        Object[] args = joinPoint.getArgs();
        sysLog.setParams(Arrays.toString(args));

        // 日志类型描述
        sysLog.setDescription(LogUtil.getControllerMethodDescription(joinPoint));

        long endTime = Instant.now().toEpochMilli();
        sysLog.setConsumingTime(endTime - beginTime);
    }

    @AfterReturning(returning = "ret", pointcut = "sysLogAspect()")
    public void doAfterReturning(Object ret) {
        // 得到当前线程的log对象
        SysLog sysLog = sysLogThreadLocal.get();
        R r = Convert.convert(R.class, ret);
        if (r.getCode() == HttpStatus.OK.value()) {
            // 正常返回
            sysLog.setType(NORMAL);
        } else {
            sysLog.setType(ABNORMAL);
            sysLog.setExDetail(r.getMsg());
        }
        // 发布事件,待消费者记录入库
        applicationContext.publishEvent(new SysLogEvent(sysLog));
        // 移除当前log实体
        sysLogThreadLocal.remove();
    }

    @AfterThrowing(throwing = "e", pointcut = "sysLogAspect()")
    public void doAfterThrowable(Throwable e) {
        SysLog sysLog = sysLogThreadLocal.get();
        // 异常
        sysLog.setType(ABNORMAL);
        sysLog.setExDetail(LogUtil.getStackTrace(e));
        sysLog.setExDetail(e.getMessage());
        // 发布事件,待消费者记录入库
        applicationContext.publishEvent(new SysLogEvent(sysLog));
        // 移除当前log实体
        sysLogThreadLocal.remove();
    }

}
