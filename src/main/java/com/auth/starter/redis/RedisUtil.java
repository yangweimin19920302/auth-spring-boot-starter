package com.auth.starter.redis;

import com.auth.starter.ConfigurationManagement;
import com.auth.starter.exception.ConnectErrorException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static JedisPool jedisPool;
	private static JedisPoolConfig jedisPoolConfig;

	private RedisUtil() {

	}

	/**
	 * 设置连接池
	 * @return
	 */
	public synchronized static JedisPool getPool() {
		if (jedisPool == null) {
			RedisConfig redisConfig = ConfigurationManagement.getRedisConfig();
			jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(redisConfig.getMaxTotal());
			jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
			jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
			jedisPoolConfig.setMinIdle(redisConfig.getMinIdle());
			jedisPoolConfig.setBlockWhenExhausted(redisConfig.getBlockWhenExhausted());
			jedisPoolConfig.setEvictionPolicyClassName(redisConfig.getEvictionPolicyClassName());
			jedisPoolConfig.setFairness(redisConfig.getFairness());
			jedisPoolConfig.setJmxEnabled(redisConfig.getJmxEnabled());
			jedisPoolConfig.setJmxNameBase(redisConfig.getJmxNameBase());
			jedisPoolConfig.setJmxNamePrefix(redisConfig.getJmxNamePrefix());
			jedisPoolConfig.setLifo(redisConfig.getLifo());
			jedisPoolConfig.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis());
			jedisPoolConfig.setNumTestsPerEvictionRun(redisConfig.getNumTestsPerEvictionRun());
			jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis());
			jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redisConfig.getTimeBetweenEvictionRunsMillis());
			jedisPoolConfig.setTestOnBorrow(redisConfig.getTestOnBorrow());
			jedisPoolConfig.setTestOnCreate(redisConfig.getTestOnCreate());
			jedisPoolConfig.setTestOnReturn(redisConfig.getTestOnReturn());
			jedisPoolConfig.setTestWhileIdle(redisConfig.getTestWhileIdle());
			jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getIp(), redisConfig.getPort());
		}
		return jedisPool;
	}

	/**
	 * 存储数据
	 * @param token
	 * @param object
	 * @throws Exception
	 */
	public static void add(String token, String object) throws ConnectErrorException {
		RedisConfig redisConfig = ConfigurationManagement.getRedisConfig();
		JedisPool pool = RedisUtil.jedisPool;
		if (pool == null) {
			pool = getPool();
		}
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(token, object);
			jedis.pexpire(token, redisConfig.getTimeOut());//设置过期时间
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectErrorException("redis连接错误");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 获取数据
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static String get(String token) throws ConnectErrorException {
		JedisPool pool = RedisUtil.jedisPool;
		if (pool == null) {
			pool = getPool();
		}
		Jedis jedis = null;
		String object;
		try {
			jedis = pool.getResource();
			object = jedis.get(token);
			if (object != null) {
				jedis.pexpire(token, ConfigurationManagement.getRedisConfig().getTimeOut());//重置过期时间
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectErrorException("redis连接错误");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return object;
	}

	/**
	 * 删除数据
	 * @param token
	 * @throws Exception
	 */
	public static void del(String token) throws ConnectErrorException {
		JedisPool pool = RedisUtil.jedisPool;
		if (pool == null) {
			pool = getPool();
		}
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(token);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConnectErrorException("redis连接错误");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}

