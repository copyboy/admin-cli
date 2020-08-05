package com.ronhan.admin.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * IP工具类
 *
 * @author qingdong.zhang
 * @version 1.0
 * @since 2020-07-13 18:52
 */
@UtilityClass
@Slf4j
public class IPUtil {

    public String getCityInfo(String ip) {

        //db
        ClassPathResource resource = new ClassPathResource("/ip2region/ip2region.db");
        try (InputStream inputStream = resource.getInputStream()) {

            String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
            String dbPath = tmpDir + "ip.db";
            File file = new File(dbPath);

            FileUtils.copyInputStreamToFile(inputStream, file);

            //查询算法
            int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
            //DbSearcher.BINARY_ALGORITHM //Binary
            //DbSearcher.MEMORY_ALGORITYM //Memory

            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());

            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                log.error("Ops! Error: Invalid ip address [{}]", ip);
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);

            return ObjectUtil.isNull(dataBlock)?dataBlock.getRegion():"Unknown";
        } catch (Exception e) {
            log.error("Ops!", e);
        }
        return null;
    }
}
