package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.modules.sys.dto.UserDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 通过用户名查找用户个人信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUserInfoName(String username);

    /**
     * 注册用户
     */
    boolean register(UserDTO userDTO);


    /**
     * 账户密码登录
     */
    String login(String username, String password);
}
