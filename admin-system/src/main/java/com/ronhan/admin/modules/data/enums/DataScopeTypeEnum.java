package com.ronhan.admin.modules.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限枚举
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 17:49
 */
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum {
    /**
     * 全部
     */
    ALL(1, "全部"),
    /**
     * 本级
     */
    THIS_LEVEL(2, "本级"),

    /**
     * 本级以及子级
     */
    THIS_LEVEL_CHILDREN(3, "本级以及子级"),
    /**
     * 自定义
     */
    CUSTOMIZE(4, "自定义");


    private int type;
    private String description;


    public static DataScopeTypeEnum valueOf(int type) {
        for (DataScopeTypeEnum typeVar : DataScopeTypeEnum.values()) {
            if (typeVar.getType() == type) {
                return typeVar;
            }
        }
        return ALL;
    }
}
