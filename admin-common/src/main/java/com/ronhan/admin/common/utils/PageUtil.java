package com.ronhan.admin.common.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-14 15:28
 */
@UtilityClass
public class PageUtil extends cn.hutool.core.util.PageUtil {

    /**
     * 分页
     */
    public List<?> toPage(int page, int size, List<?> list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if (fromIndex > list.size()) {
            return new ArrayList<>();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

}
