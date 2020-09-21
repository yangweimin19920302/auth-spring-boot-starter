package com.auth.starter.redis;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2017/8/22.
 */
public class RedisConfig extends JedisPoolConfig {

    private String ip = "127.0.0.1";
    private int port = 6379;
    private String password = null;
    //设置redis数据过期时间,单位毫秒
    private long timeOut = 3600000;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
