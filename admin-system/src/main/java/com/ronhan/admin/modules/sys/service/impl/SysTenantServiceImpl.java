package com.ronhan.admin.modules.sys.service.impl;

import com.ronhan.admin.modules.sys.domain.SysTenant;
import com.ronhan.admin.modules.sys.mapper.SysTenantMapper;
import com.ronhan.admin.modules.sys.service.ISysTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

}
