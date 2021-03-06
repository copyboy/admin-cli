package com.ronhan.admin.modules.data.strategy;

import com.ronhan.admin.modules.data.enums.DataScopeTypeEnum;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 18:05
 */
@Component("4")
public class CustomizeDataScope implements AbstractDataScopeHandler {

    private ISysDeptService deptService;

    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {

        List<Integer> roleDeptIds = roleDto.getRoleDepts();
        List<Integer> ids = new ArrayList<>();
        for (Integer deptId : roleDeptIds) {
            ids.addAll(deptService.selectDeptIds(deptId));
        }
        Set<Integer> set = new HashSet<>(ids);
        ids.clear();
        ids.addAll(set);
        return ids;
    }
}
