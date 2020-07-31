package com.ronhan.admin.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门 DTO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 11:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDTO {

    private static final long serialVersionUID = 1L;

    private Integer deptId;

    /**
     * 部门名称
     */
    private String name;


    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;
}
