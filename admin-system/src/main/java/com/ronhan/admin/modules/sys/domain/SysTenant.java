package com.ronhan.admin.modules.sys.domain;

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
 * 租户表
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_tenant")
public class SysTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户编号
     */
    private String code;

    /**
     * 授权开始时间
     */
    private LocalDateTime startTime;

    /**
     * 授权结束时间
     */
    private LocalDateTime endTime;

    /**
     * 0正常 9-冻结
     */
    private Integer status;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
