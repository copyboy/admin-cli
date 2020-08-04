package com.ronhan.admin.modules.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security配置类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-03 10:26
 */
@Configuration
@EnableWebSecurity
public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 暴露AuthenticationManager Bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
