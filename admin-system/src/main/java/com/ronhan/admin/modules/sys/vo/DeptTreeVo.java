package com.ronhan.admin.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 构建部门树 VO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 16:57
 */
@Setter
@Getter
@ToString
public class DeptTreeVo {

    private int id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptTreeVo> children;

}