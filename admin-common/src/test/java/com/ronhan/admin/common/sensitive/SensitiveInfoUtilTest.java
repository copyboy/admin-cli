package com.ronhan.admin.common.sensitive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 敏感信息工具类 测试
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 17:39
 */
class SensitiveInfoUtilTest {

    @Test
    void chineseName_2_char_test() {
        String given = "张三";

        String ret = SensitiveInfoUtil.chineseName(given);
        String expect = "张*";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void chineseName_3_char_test() {
        String given = "张三四";

        String ret = SensitiveInfoUtil.chineseName(given);
        String expect = "张**";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void chineseName_two_part_test() {

        String ret = SensitiveInfoUtil.chineseName("张", "三民");
        String expect = "张**";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void idCardNum_18_test() {
        String given = "510101199911010094";

        String ret = SensitiveInfoUtil.idCardNum(given);
        String expect = "510************094";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void idCardNum_15_test() {
        String given = "510101991101009";

        String ret = SensitiveInfoUtil.idCardNum(given);
        String expect = "510*********009";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void idCardNum_length_short_test() {
        String given = "1234";

        String ret = SensitiveInfoUtil.idCardNum(given);
        String expect = "";

        Assertions.assertEquals(expect, ret);
    }

    @Test
    void fixed_phone_test() {
        String given = "027-83777166";
        String ret = SensitiveInfoUtil.fixedPhone(given);
        Assertions.assertEquals("********7166", ret);
    }

    @Test
    void mobile_phone_test() {
        String given = "13012344321";
        String ret = SensitiveInfoUtil.fixedPhone(given);
        Assertions.assertEquals("*******4321", ret);
    }

    @Test
    void address_test(){
        String given = "武汉东湖新技术开发区光谷大道3号激光工程设计总部二期研发楼06幢06单元15层5号";
        String ret = SensitiveInfoUtil.address(given, 25);
        Assertions.assertEquals("武汉东湖新技术开发区光谷大道3号*************************", ret);
    }

    @Test
    void email_test(){
        String given = "abc@qq.com";
        String ret = SensitiveInfoUtil.email(given);
        Assertions.assertEquals("a**@qq.com", ret);
    }

    @Test
    void bank_card_test(){
        String given = "62141002003008888";
        String ret = SensitiveInfoUtil.bankCard(given);
        Assertions.assertEquals("621410*******8888", ret);
    }

    @Test
    void cn_aps_test(){
        String given = "103249438208";
        String ret = SensitiveInfoUtil.cnapsCode(given);
        Assertions.assertEquals("103**8208", ret);
    }

}