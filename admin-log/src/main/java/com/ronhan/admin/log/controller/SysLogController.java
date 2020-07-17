package com.ronhan.admin.log.controller;


import com.ronhan.admin.common.utils.R;
import com.ronhan.admin.log.annotation.SysOperaLog;
import com.ronhan.admin.log.service.ISysLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Resource
    private ISysLogService logService;

    /**
     * 分页查询日志列表
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('sys:log:view')")
    public R selectLog(@RequestParam("page") Integer page,
                       @RequestParam("pageSize") Integer pageSize,
                       @RequestParam("type") Integer type,
                       @RequestParam("userName") String userName) {
        return R.ok(logService.selectLogList(page, pageSize, type, userName));
    }

    @SysOperaLog(description = "删除日志")
    @DeleteMapping("/{logId}")
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public R delete(@PathVariable("logId") Integer logId) {
        return R.ok(logService.removeById(logId));
    }
}

