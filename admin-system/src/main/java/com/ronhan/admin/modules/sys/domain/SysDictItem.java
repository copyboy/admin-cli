package com.ronhan.admin.modules.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典详情表
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_item")
public class SysDictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典项文本
     */
    private String itemText;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
