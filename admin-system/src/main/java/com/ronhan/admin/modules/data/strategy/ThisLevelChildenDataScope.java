package com.ronhan.admin.modules.data.strategy;

import com.ronhan.admin.modules.data.enums.DataScopeTypeEnum;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.ronhan.admin.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本级以及子级
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 18:05
 */
@Component("3")
public class ThisLevelChildenDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;


    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        Integer deptId = userService.findByUserInfoName(SecurityUtil.getUser().getUsername()).getDeptId();
        return deptService.selectDeptIds(deptId);
    }
}
