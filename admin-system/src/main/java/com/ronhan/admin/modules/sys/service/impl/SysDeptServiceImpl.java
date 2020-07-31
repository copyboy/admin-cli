package com.ronhan.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ronhan.admin.modules.sys.domain.SysDept;
import com.ronhan.admin.modules.sys.mapper.SysDeptMapper;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<Integer> selectDeptIds(int deptId) {
        SysDept department = this.getDepartment(deptId);
        List<Integer> deptIdList = new ArrayList<>();
        if (department != null) {
            deptIdList.add(department.getDeptId());
            addDeptIdList(deptIdList, department);
        }
        return deptIdList;
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        return baseMapper.selectOne(Wrappers.<SysDept>query().lambda().select(SysDept::getName).eq(SysDept::getDeptId, deptId)).getName();
    }

    /**
     * 根据部门ID获取该部门及其下属部门树
     */
    private SysDept getDepartment(Integer deptId) {
        List<SysDept> departments = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Map<Integer, SysDept> map = departments.stream().collect(
                Collectors.toMap(SysDept::getDeptId, department -> department));

        for (SysDept dept : map.values()) {
            SysDept parent = map.get(dept.getParentId());
            if (parent != null) {
                List<SysDept> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(dept);
                parent.setChildren(children);
            }
        }
        return map.get(deptId);
    }

    private void addDeptIdList(List<Integer> deptIdList, SysDept department) {
        List<SysDept> children = department.getChildren();
        if (children != null) {
            for (SysDept d : children) {
                deptIdList.add(d.getDeptId());
                addDeptIdList(deptIdList, d);
            }
        }
    }

}
