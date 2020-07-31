package com.ronhan.admin.modules.sys.util;

import com.ronhan.admin.modules.sys.domain.SysDept;
import com.ronhan.admin.modules.sys.vo.DeptTreeVo;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin 系统用户工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 15:01
 */
@UtilityClass
public class AdminUtil {

    /**
     * 生成BCryptPasswordEncoder密码
     */
    public String encode(String rawPass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPass);
    }

    /**
     * 构建部门tree
     */
    public void findChildren(List<SysDept> sysDeptList, List<SysDept> deptList) {

        for (SysDept sysDept : sysDeptList) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : deptList) {
                if (sysDept.getDeptId() != null
                        && sysDept.getDeptId().equals(dept.getParentId())) {
                    dept.setParentName(sysDept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, deptList);
        }
    }

    /**
     * 构建部门tree
     */
    public void findChildren1(List<DeptTreeVo> treeVos, List<SysDept> deptList) {

        for (DeptTreeVo sysDept : treeVos) {
            sysDept.setId(sysDept.getId());
            sysDept.setLabel(sysDept.getLabel());
            List<DeptTreeVo> children = new ArrayList<>();
            for (SysDept dept : deptList) {
                if (sysDept.getId() == dept.getParentId()) {
                    DeptTreeVo deptTreeVo1 = new DeptTreeVo();
                    deptTreeVo1.setLabel(dept.getName());
                    deptTreeVo1.setId(dept.getDeptId());
                    children.add(deptTreeVo1);
                }
            }
            sysDept.setChildren(children);
            findChildren1(children, deptList);
        }
    }
}
