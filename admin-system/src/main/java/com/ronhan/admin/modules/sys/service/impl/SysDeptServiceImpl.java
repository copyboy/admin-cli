package com.ronhan.admin.modules.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ronhan.admin.modules.sys.domain.SysDept;
import com.ronhan.admin.modules.sys.dto.DeptDTO;
import com.ronhan.admin.modules.sys.mapper.SysDeptMapper;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ronhan.admin.modules.sys.util.AdminUtil;
import com.ronhan.admin.modules.sys.vo.DeptTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public List<SysDept> selectDeptList() {
        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery().select(SysDept::getDeptId, SysDept::getName, SysDept::getParentId, SysDept::getSort, SysDept::getCreateTime));
        List<SysDept> sysDeptList = deptList.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());
        AdminUtil.findChildren(sysDeptList, deptList);
        return sysDeptList;
    }

    @Override
    public List<DeptTreeVo> getDeptTree() {
        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        List<DeptTreeVo> collect = deptList.stream().filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .map(sysDept -> {
                    DeptTreeVo deptTreeVo = new DeptTreeVo();
                    deptTreeVo.setId(sysDept.getDeptId());
                    deptTreeVo.setLabel(sysDept.getName());
                    return deptTreeVo;

                }).collect(Collectors.toList());

        AdminUtil.findChildren1(collect,deptList);
        return collect;
    }

    @Override
    public boolean updateDeptById(DeptDTO deptDto) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptDto, sysDept);
        sysDept.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysDept);
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
