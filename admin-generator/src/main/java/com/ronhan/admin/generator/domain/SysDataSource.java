package com.ronhan.admin.generator.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源信息
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 16:09
 */
@Data
public class SysDataSource {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * jdbcurl
     */
    private String url;

    /**
     * 驱动
     */
    private String driverName;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 更新
     */
    private LocalDateTime updateDate;
}
