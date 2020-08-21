package com.ronhan.admin.modules.sys.service;

import com.ronhan.admin.modules.sys.domain.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ronhan.admin.modules.sys.domain.SysDictItem;
import com.ronhan.admin.modules.sys.dto.DictDTO;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 根据字典名称查询字段详情
     */
    List<SysDictItem> queryDictItemByDictName(String dictName);

    /**
     * 修改字典
     */
    boolean updateDict(DictDTO dictDto);
}
