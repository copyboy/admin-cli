package com.ronhan.admin.modules.data.tenant;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * 多租户上下文
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 11:32
 */
@UtilityClass
public class TenantContextHolder {

    private static final String KEY_CURRENT_TENANT_ID = "KEY_CURRENT_TENANT_ID";
    private static final Map<String, Object> TENANT_CONTEXT = Maps.newConcurrentMap();

    /**
     * 设置租户Id
     */
    public void setCurrentTenantId(Long providerId) {
        TENANT_CONTEXT.put(KEY_CURRENT_TENANT_ID, providerId);
    }

    /**
     * 获取租户Id
     */
    public Long getCurrentTenantId() {
        return (Long) TENANT_CONTEXT.get(KEY_CURRENT_TENANT_ID);
    }

    /**
     * 清除租户信息
     */
    public void clear() {
        TENANT_CONTEXT.clear();
    }
}
