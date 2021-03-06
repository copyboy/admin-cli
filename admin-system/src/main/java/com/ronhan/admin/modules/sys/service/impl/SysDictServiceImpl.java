package com.ronhan.admin.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ronhan.admin.modules.sys.domain.SysDict;
import com.ronhan.admin.modules.sys.domain.SysDictItem;
import com.ronhan.admin.modules.sys.dto.DictDTO;
import com.ronhan.admin.modules.sys.mapper.SysDictMapper;
import com.ronhan.admin.modules.sys.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author qingdong.zhang
 * @since 2020-07-31
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public List<SysDictItem> queryDictItemByDictName(String dictName) {
        return baseMapper.queryDictItemByDictName(dictName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDict(DictDTO dictDto) {
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dictDto, sysDict);
        return updateById(sysDict);
    }
}
