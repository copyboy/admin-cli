package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysTenantService extends IService<SysTenant> {

    /**
     * 保存租户
     */
    boolean saveTenant(SysTenant sysTenant);


    /**
     * 获取正常租户
     */
    List<SysTenant> getNormalTenant();
}
