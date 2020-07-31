package com.ronhan.admin.modules.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典 DTO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictDTO {

    private Integer id;

    private String dictName;

    private String dictCode;

    private String description;

    private Integer sort;

    private String remark;
}
