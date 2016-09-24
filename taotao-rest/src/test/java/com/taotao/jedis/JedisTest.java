package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@Test
	public void jedisTest(){
		//创建一个jedis对象
		Jedis jedis = new Jedis("192.168.146.128", 6379);
		//调用jedis对象的方法，方法名和jedis的命令名一直
		jedis.set("test", "test1");
		String str = jedis.get("test");
		System.out.println(str);
		//关闭jedis
		jedis.close();
	}
	/**
	 * 使用jedis连接池
	 */
	@Test
	public void  testJedisPoll() {
		//创建连接池
		JedisPool pool = new JedisPool("192.168.146.128", 6379);
		//获取jedis对象
		Jedis jedis = pool.getResource();
		//使用jedis对象
		jedis.set("pool", "jedis poll");
		System.out.println(jedis.get("pool"));
		//关闭jedis
		jedis.close();
		//关闭连接池
		pool.close();
	}
	
	public void testJedisCluster(){
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		HostAndPort e = new HostAndPort("", 2);
		nodes.add(e);
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.get("");
	}
}
