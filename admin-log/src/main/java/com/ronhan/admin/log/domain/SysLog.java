package com.ronhan.admin.log.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统日志实体类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作类型 1 操作记录 2异常记录
     */
    private Integer type;

    /**
     * 操作人
     */
    private String userName;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String actionMethod;

    private String actionUrl;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 消耗时间
     */
    private Long consumingTime;

    /**
     * 异常详情信息
     */
    private String exDesc;

    /**
     * 异常描述
     */
    private String exDetail;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 操作系统
     */
    private String os;


}
