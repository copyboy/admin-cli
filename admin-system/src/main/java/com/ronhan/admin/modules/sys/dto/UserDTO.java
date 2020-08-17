package com.ronhan.admin.modules.sys.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 用户 DTO
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-31 12:01
 */
@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {

    private Integer userId;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    private String password;
    private Integer deptId;
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @Email
    private String email;
    private String avatar;
    private String lockFlag;
    private String delFlag;
    private List<Integer> roleList;
    private List<Integer> deptList;
    /**
     * 新密码
     */
    private String newPassword;
    private String smsCode;
}
