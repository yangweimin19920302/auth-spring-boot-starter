package com.auth.starter;

import com.auth.starter.annotation.LoginType;
import com.auth.starter.annotation.StorageMedium;
import com.auth.starter.ehcache.EhcacheConfig;
import com.auth.starter.redis.RedisConfig;

/**
 * Created by Administrator on 2017/8/22.
 */
public class ConfigurationManagement {

    //LoginType.MANY表示一个账号可以多点登录；LoginType.ONE为一个账号只能在一个点登录，（即，后面登录的人会把前面登录的人踢出登录）
    private static LoginType loginType = LoginType.ONE;
    //StorageMedium.EHCACHE为ehcache缓存,StorageMedium.REDIS为redis缓存
    private static StorageMedium storageMedium = StorageMedium.EHCACHE;
    private static RedisConfig redisConfig = new RedisConfig();
    private static EhcacheConfig ehcacheConfig = new EhcacheConfig();

    private ConfigurationManagement() {}

    public static RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public static EhcacheConfig getEhcacheConfig() {
        return ehcacheConfig;
    }

    public static StorageMedium getStorageMedium() {
        return storageMedium;
    }

    public static void setStorageMedium(StorageMedium storageMedium) {
        ConfigurationManagement.storageMedium = storageMedium;
    }

    public static LoginType getLoginType() {
        return loginType;
    }

    public static void setLoginType(LoginType loginType) {
        ConfigurationManagement.loginType = loginType;
    }
}
