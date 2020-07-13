package com.ronhan.admin.common.sensitive;

/**
 * 敏感信息类型
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 17:14
 */
public enum SensitiveType {

    /**
     * 中文名
     */
    CHINESE_NAME,

    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * China National Advanced Payment System
     * 公司开户银行联号
     */
    CNAPS_CODE
}
