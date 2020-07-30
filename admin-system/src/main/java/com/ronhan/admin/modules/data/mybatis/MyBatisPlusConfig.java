package com.ronhan.admin.modules.data.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * MyBatisPlusConfig
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 18:00
 */
@Configuration
@MapperScan({"com.ronhan.admin.**.mapper"})
public class MyBatisPlusConfig {



}
