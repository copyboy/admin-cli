package com.ronhan.admin.common.constant;

/**
 * 菜单枚举类型
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 16:34
 */
public enum MenuTypeEnum {

    CATALOG(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    ;

    private final int value;
    private final String desc;

    MenuTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
