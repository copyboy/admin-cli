package com.ronhan.admin.modules.data.strategy;

import com.ronhan.admin.modules.data.enums.DataScopeTypeEnum;
import com.ronhan.admin.modules.sys.domain.SysDept;
import com.ronhan.admin.modules.sys.dto.RoleDTO;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 所有数据查询域
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 18:00
 */
@Component("1")
public class AllDataScope implements AbstractDataScopeHandler {

    @Resource
    private ISysDeptService deptService;

    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        List<SysDept> sysDeptList = deptService.list();
        return sysDeptList.stream().map(SysDept::getDeptId).collect(Collectors.toList());
    }
}
