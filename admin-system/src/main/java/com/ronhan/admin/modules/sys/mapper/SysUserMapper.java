package com.ronhan.admin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ronhan.admin.modules.data.datascope.DataScope;
import com.ronhan.admin.modules.sys.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ronhan.admin.modules.sys.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    @Insert("insert into sys_user (username,password,dept_id,job_id,phone,email,avatar,lock_flag) values (#{username},#{password},#{deptId},#{jobId},#{phone},#{email},#{avatar},#{lockFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    boolean insertUser(SysUser sysUser);


    /**
     * 分页查询用户信息（含角色）
     *
     * @param page      分页
     * @param userDTO   查询参数
     * @param dataScope 数据域
     * @return list
     */
    IPage<SysUser> getUserVosPage(Page<SysUser> page, @Param("query") UserDTO userDTO, DataScope dataScope);

}
