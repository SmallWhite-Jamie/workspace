package com.taotao.rest.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.taotao.rest.dao.JedisClient;

public class JedisClientSingle implements JedisClient{
	
	@Autowired
	private JedisPool pool;
	
	@Override
	public String get(String key) {
		Jedis resource = pool.getResource();
		String str = resource.get(key);
		resource.close();
		return str;
	}

	@Override
	public String set(String key, String value) {
		Jedis resource = pool.getResource();
		String set = resource.set(key, value);
		resource.close();
		return set;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis resource = pool.getResource();
		String hget = resource.hget(hkey, key);
		resource.close();
		return hget;
	}

	@Override
	public Long hset(String hkey, String key, String value) {
		Jedis resource = pool.getResource();
		Long hset = resource.hset(hkey, key, value);
		resource.close();
		return hset;
	}

	@Override
	public Long incr(String key) {
		Jedis resource = pool.getResource();
		Long incr = resource.incr(key);
		resource.close();
		return incr;
	}

	@Override
	public Long expire(String key, int second) {
		Jedis resource = pool.getResource();
		Long expire = resource.expire(key, second);
		resource.close();
		return expire;
	}

	@Override
	public Long del(String key) {
		Jedis resource = pool.getResource();
		Long del = resource.del(key);
		resource.close();
		return del;
	}

	@Override
	public Long hdel(String hkey, String key) {
		Jedis resource = pool.getResource();
		Long hdel = resource.hdel(hkey, key);
		return hdel;
	}

}
