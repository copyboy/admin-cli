package com.ronhan.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ronhan.admin.modules.sys.domain.SysUser;
import com.ronhan.admin.modules.sys.mapper.SysUserMapper;
import com.ronhan.admin.modules.sys.service.ISysDeptService;
import com.ronhan.admin.modules.sys.service.ISysUserRoleService;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private ISysDeptService deptService;

    @Override
    public SysUser findByUserInfoName(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPhone, SysUser::getEmail, SysUser::getPassword, SysUser::getDeptId, SysUser::getJobId, SysUser::getAvatar)
                .eq(SysUser::getUsername, username));
        // 获取部门
        sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
        // 获取岗位
//        sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        return sysUser;
    }
}
