package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysDeptService extends IService<SysDept> {


    /**
     * 通过此部门id查询于此相关的部门ids
     */
    List<Integer> selectDeptIds(int deptId);

    /**
     * 根据部门id查询部门名称
     * @param deptId
     * @return
     */
    String selectDeptNameByDeptId(int deptId);

}
