package com.ronhan.admin.modules.data.strategy;

import com.ronhan.admin.modules.data.enums.DataScopeTypeEnum;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.ronhan.admin.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 本级
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 18:05
 */
@Component("2")
public class ThisLevelDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        // 用于存储部门id
        List<Integer> deptIds = new ArrayList<>();
        deptIds.add(userService.findByUserInfoName(SecurityUtil.getUser().getUsername()).getDeptId());
        return deptIds;
    }
}
