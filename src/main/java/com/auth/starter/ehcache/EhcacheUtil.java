package com.auth.starter.ehcache;

import com.auth.starter.ConfigurationManagement;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by Administrator on 2017/8/22.
 */
public class EhcacheUtil {

    private static  CacheManager cacheManager;

    private EhcacheUtil() {}

    /**
     * 创建缓存管理
     * @return
     * @throws Exception
     */
    public synchronized static CacheManager getCacheManager() {
        if (cacheManager == null) {
            EhcacheConfig ehcacheConfig = ConfigurationManagement.getEhcacheConfig();
            cacheManager = CacheManager.create(ehcacheConfig.getUrl(ehcacheConfig.getPath()));
        }
        return cacheManager;
    }

    /**
     * 保存数据
     * @param token
     * @param object
     * @throws Exception
     */
    public static void add(String token, String object) {
        CacheManager cacheManager = EhcacheUtil.cacheManager;
        if (cacheManager == null) {
            cacheManager = EhcacheUtil.getCacheManager();
        }
        String names[] = cacheManager.getCacheNames();
        Cache cache = cacheManager.getCache(names[names.length - 1]);//如果配置文件有多个缓存，那么就默认用第一个缓存(配置文件中的第一个缓存，放在数组的最后)
        Element element = new Element(token, object);
        cache.put(element);
    }

    /**
     * 获取数据
     * @param token
     * @return
     * @throws Exception
     */
    public static String get(String token) {
        CacheManager cacheManager = EhcacheUtil.cacheManager;
        if (cacheManager == null) {
            cacheManager = EhcacheUtil.getCacheManager();
        }
        String names[] = cacheManager.getCacheNames();
        Cache cache = cacheManager.getCache(names[names.length - 1]);
        Element element = cache.get(token);
        if (element == null || element.getObjectValue() == null) {
            return null;
        }
        add(token, element.getObjectValue().toString());
        return element.getObjectValue().toString();
    }

    /**
     * 删除数据
     * @param token
     * @throws Exception
     */
    public static void del(String token) {
        CacheManager cacheManager = EhcacheUtil.cacheManager;
        if (cacheManager == null) {
            cacheManager = EhcacheUtil.getCacheManager();
        }
        String names[] = cacheManager.getCacheNames();
        Cache cache = cacheManager.getCache(names[names.length - 1]);
        cache.remove(token);
    }
}
