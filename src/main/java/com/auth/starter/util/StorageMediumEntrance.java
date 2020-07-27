package com.auth.starter.util;

import com.auth.starter.ConfigurationManagement;
import com.auth.starter.annotation.StorageMedium;
import com.auth.starter.ehcache.EhcacheUtil;
import com.auth.starter.exception.ConnectErrorException;
import com.auth.starter.redis.RedisUtil;

/**
 * redis和ehcache统一入口
 */
public class StorageMediumEntrance {

    /**
     * 插入(固定过期时间)
     * @param token
     * @param object
     * @throws ConnectErrorException
     */
    public static void add(String token, String object) throws ConnectErrorException {
        if (ConfigurationManagement.getStorageMedium().equals(StorageMedium.REDIS)) {
            RedisUtil.add(token, object);
        } else {
            EhcacheUtil.add(token, object);
        }
    }

    /**
     * 插入(手动设置过期时间)
     * @param token
     * @param object
     * @throws ConnectErrorException
     */
    public static void add(String token, String object, Long timeout) throws ConnectErrorException {
        if (ConfigurationManagement.getStorageMedium().equals(StorageMedium.REDIS)) {
            RedisUtil.add(token, object, timeout);
        } else {
            EhcacheUtil.add(token, object);
        }
    }

    /**
     * 获取（固定更新时间）
     * @param token
     * @throws ConnectErrorException
     */
    public static String get(String token) throws ConnectErrorException {
        if (ConfigurationManagement.getStorageMedium().equals(StorageMedium.REDIS)) {
            return RedisUtil.get(token);
        } else {
            return EhcacheUtil.get(token);
        }
    }

    /**
     * 获取(自定义更新时间)
     * @param token
     * @param timeout
     * @return
     * @throws ConnectErrorException
     */
    public static String get(String token, Long timeout) throws ConnectErrorException {
        if (ConfigurationManagement.getStorageMedium().equals(StorageMedium.REDIS)) {
            return RedisUtil.get(token, timeout);
        } else {
            return EhcacheUtil.get(token);
        }
    }

    /**
     * 删除
     * @param token
     * @throws ConnectErrorException
     */
    public static void del(String token) throws ConnectErrorException {
        if (ConfigurationManagement.getStorageMedium().equals(StorageMedium.REDIS)) {
            RedisUtil.del(token);
        } else {
            EhcacheUtil.del(token);
        }
    }
}
