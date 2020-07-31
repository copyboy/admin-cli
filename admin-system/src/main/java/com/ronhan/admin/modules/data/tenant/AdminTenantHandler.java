package com.ronhan.admin.modules.data.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 租户处理器 -主要实现 mybatis-plus https://mp.baomidou.com/guide/tenant.html
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 11:43
 */
@Slf4j
@Component
public class AdminTenantHandler implements TenantHandler {

    @Resource
    private TenantConfigProperties configProperties;

    /**
     * 租户Id
     */
    @Override
    public Expression getTenantId(boolean where) {
        Long tenantId = TenantContextHolder.getCurrentTenantId();
        log.debug("当前租户为{}", tenantId);
        if (tenantId == null) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    /**
     * 租户字段名
     */
    @Override
    public String getTenantIdColumn() {
        return configProperties.getTenantIdColumn();
    }
    /**
     * 根据表名判断是否进行过滤
     * 忽略掉一些表：如租户表（sys_tenant）本身不需要执行这样的处理
     */
    @Override
    public boolean doTableFilter(String tableName) {
        return configProperties.getIgnoreTenantTables().stream().anyMatch((e) -> e.equalsIgnoreCase(tableName));
    }
}
