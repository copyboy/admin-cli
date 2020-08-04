package com.ronhan.admin.modules.security.config;

import com.ronhan.admin.modules.security.UserDetailsServiceImpl;
import com.ronhan.admin.modules.security.handle.AdminAccessDeniedHandler;
import com.ronhan.admin.modules.security.handle.AdminAuthenticationEntryPointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * Security配置类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-08-03 10:26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 暴露AuthenticationManager Bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * HTTP 授权
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            // 使用JWT，不需要CSRF配置
            .csrf().disable()
            // 短信登陆配置
//                .apply().and()
            // 社交登陆
//                .apply().and()
            // 基于Token，不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // 过滤请求
            .authorizeRequests()
            // 登陆,注册,icon 允许匿名访问
            .antMatchers("/login/**", "/register/**", "favicon.ico").anonymous()
            // 静态资源访问
            .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
            // 除上面外的所有请求都需要鉴权认证
            .anyRequest().authenticated().and()
            // 禁止iframe
            .headers().frameOptions().disable();

        // 添加自定义异常入口
        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(new AdminAuthenticationEntryPointImpl())
                .accessDeniedHandler(new AdminAccessDeniedHandler());

        // 添加 JWT filter 用户名登陆
//        httpSecurity
                // 添加图形验证码校验过滤器
//                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT验证过滤器
//                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加短信验证码过滤器
//                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * 认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
