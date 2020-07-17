package com.ronhan.admin.log.event;

import com.ronhan.admin.log.domain.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 17:03
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(SysLog sysLog) {
        super(sysLog);
    }
}
