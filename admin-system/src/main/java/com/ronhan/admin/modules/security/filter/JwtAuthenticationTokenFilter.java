package com.ronhan.admin.modules.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.ronhan.admin.modules.security.util.JwtUtil;
import com.ronhan.admin.modules.sys.service.ISysUserService;
import com.ronhan.admin.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Token 过滤器
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-17 17:18
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ISysUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityUser securityUser = jwtUtil.getUserFromToken(request);
        if (ObjectUtil.isNotNull(securityUser)){
            Set<String> permissions = userService.findPermsByUserId(securityUser.getUserId());
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(permissions.toArray(new String[0]));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
