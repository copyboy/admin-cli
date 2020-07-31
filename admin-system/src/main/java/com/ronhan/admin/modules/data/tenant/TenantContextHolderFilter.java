package com.ronhan.admin.modules.data.tenant;

import cn.hutool.core.util.StrUtil;
import com.ronhan.admin.common.constant.AdminConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 多租户上下文过滤器 -设置加载顺序最高获取租户
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 11:33
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 后面考虑存到redis
        String tenantId = request.getHeader(AdminConstant.ADMIN_IMAGE_KEY);
        //在没有提供tenantId的情况下返回默认的
        if (StrUtil.isNotBlank(tenantId)) {
            TenantContextHolder.setCurrentTenantId(Long.valueOf((tenantId)));
        } else {
            TenantContextHolder.setCurrentTenantId(1L);
        }
        filterChain.doFilter(request, response);
    }
}
