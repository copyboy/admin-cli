package com.ronhan.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-17 17:38
 */
@SpringBootApplication
@Slf4j
public class AdminApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AdminApplication.class);
        //所有的bean,参考：http://412887952-qq-com.iteye.com/blog/2314051
        String[] beanNames = ctx.getBeanDefinitionNames();
        //String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
        int i = 0;
        for (String str : beanNames) {
            log.info("{},beanName:{}", ++i, str);
        }

    }

}
