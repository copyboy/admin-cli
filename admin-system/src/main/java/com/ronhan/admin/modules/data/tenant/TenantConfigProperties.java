package com.ronhan.admin.modules.data.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户动态配置
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 11:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "admin.tenant")
public class TenantConfigProperties {
    /**
     * 维护租户id
     */
    private String tenantIdColumn;

    /**
     * 多租户的数据表集合
     */
    private List<String> ignoreTenantTables = new ArrayList<>();
}
