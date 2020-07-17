package com.ronhan.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.log.domain.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-17
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 分页查询日志
     */
    IPage<SysLog> selectLogList(Integer page, Integer pageSize, Integer type, String userName);

}
