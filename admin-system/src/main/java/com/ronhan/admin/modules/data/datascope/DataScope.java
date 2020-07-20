package com.ronhan.admin.modules.data.datascope;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 数据权限查询域,只允许查询该部门下的权限
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 17:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataScope extends HashMap {

    /**
     * 限制范围的字段名称
     */
    private String scopeName = "deptId";

    /**
     * 具体的数据范围
     */
    private List<Integer> deptIds = new ArrayList<>();

    /**
     * 是否只查询本部门
     */
    private Boolean isOnly = false;
}
