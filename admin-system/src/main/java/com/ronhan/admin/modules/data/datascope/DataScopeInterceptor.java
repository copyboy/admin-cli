package com.ronhan.admin.modules.data.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.ronhan.admin.common.exception.BaseException;
import com.ronhan.admin.modules.data.enums.DataScopeTypeEnum;
import com.ronhan.admin.security.SecurityUser;
import com.ronhan.admin.security.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mybatis 拦截器 主要用于数据权限拦截
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 17:20
 */
@Order(90)
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {

    private final DataSource dataSource;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler handler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(handler);

        this.sqlParser(metaObject);
        // 先判断是否为SELECT操作，不是的话，直接跳过
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 原始SQL语句
        String originalSql = boundSql.getSql();
        // 原始SQL参数
        Object parameterObject = boundSql.getParameterObject();

        // 查找参数中包含DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);
        if (ObjectUtil.isNull(dataScope)) {
            return invocation.proceed();
        }

        String scopeName = dataScope.getScopeName();
        List<Integer> deptIds = dataScope.getDeptIds();

        // 优先获取赋值数据
        if (CollUtil.isEmpty(deptIds)) {
            SecurityUser user = SecurityUtil.getUser();
            if (null == user) {
                throw new BaseException("auto data scope, set up security details true");
            }
            // 解析角色Id
            List<String> roleIdList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .filter(authority -> authority.startsWith("ROLE_"))
                    .map(authority -> authority.split("_")[1])
                    .collect(Collectors.toList());
            // 通过角色Id查询范围权限
            Entity query = Db.use(dataSource)
                    .query("SELECT * FROM sys_role where role_id IN (" + CollUtil.join(roleIdList, ",") + ")")
                    .stream().min(Comparator.comparingInt(o -> o.getInt("ds_type"))).get();
            // 数据库权限范围字段
            Integer dsType = query.getInt("ds_type");
            // 查询全部
            if (DataScopeTypeEnum.ALL.getType() == dsType) {
                return invocation.proceed();
            }
            // 除全部外，其他需获取自定义，本级及其下级
            String dsScope = query.getStr("ds_scope");
            deptIds.addAll(Arrays.stream(dsScope.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList()));

            String deptIdStr = CollUtil.join(deptIds, ",");
            originalSql = "select * from (" + originalSql + ") temp_data_scope " +
                    "where temp_data_scope." + scopeName + " in (" + deptIdStr + ")";
            metaObject.setValue("delegate.boundSql.sql", originalSql);
            return invocation.proceed();

        }
        return invocation.proceed();

    }

    /**
     * 查找参数是否包括DataScope对象
     */
    private DataScope findDataScopeObject(Object parameterObject) {
        if (parameterObject instanceof DataScope) {
            return (DataScope) parameterObject;
        } else if (parameterObject instanceof Map) {
            for (Object value : ((Map<?, ?>) parameterObject).values()) {
                if (value instanceof DataScope) {
                    return (DataScope) value;
                }
            }
        }
        return null;
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {

        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
