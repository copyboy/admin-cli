package com.ronhan.admin.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单 DTO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO {

    private static final long serialVersionUID = 1L;

    private Integer menuId;
    private String name;
    private String perms;
    private String path;
    private Boolean isFrame;
    private Integer parentId;
    private String component;
    private String icon;
    private Integer sort;
    private Integer type;
    private String delFlag;

}