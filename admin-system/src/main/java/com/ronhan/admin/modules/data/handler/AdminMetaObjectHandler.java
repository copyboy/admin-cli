package com.ronhan.admin.modules.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 字段自动填充器,逻辑删除处理
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 17:55
 */
@Slf4j
@Component
public class AdminMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //避免使用metaObject.setValue()
        this.setFieldValByName("delFlag", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("operator", "Tom", metaObject);
    }
}
