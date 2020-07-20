package com.ronhan.admin.modules.sys.dto;

import com.ronhan.admin.modules.sys.domain.SysRoleMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色DTO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-20 17:59
 */
@Setter
@Getter
public class RoleDTO {

    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    private String delFlag;
    private int dsType;
    List<SysRoleMenu> roleMenus;
    List<Integer> roleDeptList;


}
