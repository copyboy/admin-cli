package com.ronhan.admin.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.List;

/**
 * 构建菜单 VO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 16:57
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo {

    private String name;
    private String path;
    private String redirect;
    private String component;
    private Boolean alwaysShow;
    private MenuMetaVo meta;
    private List<MenuVo> children;

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ1");
        String password = textEncryptor.encrypt("101.132.64.80");
        System.out.println(password);

    }
}
