package com.ronhan.admin.modules.security;

import cn.hutool.core.util.ObjectUtil;
import com.ronhan.admin.modules.sys.domain.SysUser;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.ronhan.admin.security.LoginType;
import com.ronhan.admin.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 用户身份验证
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-04 12:11
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ISysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        SysUser user = userService.findSecurityUserByUser(sysUser);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：[{}] 不存在." , username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        Collection<? extends GrantedAuthority> authorities = getUserAuthorities(user.getUserId());
        return new SecurityUser(user.getUserId(), username, user.getPassword(), authorities, LoginType.normal);
    }

    /**
     * 封装 根据用户Id获取权限
     */
    private Collection<? extends GrantedAuthority> getUserAuthorities(int userId) {
        // 获取用户拥有的角色
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        // 权限集合
        Set<String> permissions = userService.findPermsByUserId(userId);
        // 角色集合
        Set<String> roleIds = userService.findRoleIdByUserId(userId);
        permissions.addAll(roleIds);
        return AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));
    }
}
