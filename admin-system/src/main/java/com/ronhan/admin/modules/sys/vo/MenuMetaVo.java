package com.ronhan.admin.modules.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 构建菜单元素 VO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 16:57
 */
@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

    private String title;
    private String icon;
}
