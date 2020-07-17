package com.ronhan.admin.log.event;

import com.ronhan.admin.log.domain.SysLog;
import com.ronhan.admin.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注解形式的监听 异步监听日志事件
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 17:08
 */
@Slf4j
@Component
public class SysLogListener {

    @Resource
    private ISysLogService sysLogService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        sysLogService.save(sysLog);
    }

}
