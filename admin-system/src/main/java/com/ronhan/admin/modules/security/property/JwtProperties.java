package com.ronhan.admin.modules.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 属性配置文件
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-03 10:31
 */
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String header;

    private String tokenHead;

}
