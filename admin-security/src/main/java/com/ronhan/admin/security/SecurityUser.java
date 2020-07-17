package com.ronhan.admin.security;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 身份权限认证类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 14:52
 */
@Getter
@Setter
@Accessors(chain = true)
public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 3973202083921721271L;

    private LoginType loginType;

    private Long userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(LoginType loginType, Long userId, String username,
                        String password, Collection<? extends GrantedAuthority> authorities) {
        this.loginType = loginType;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    /**
     * 返回分配给用户的角色列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 账号是否过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
