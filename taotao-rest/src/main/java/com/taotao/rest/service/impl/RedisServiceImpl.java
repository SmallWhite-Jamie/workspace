package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.TaotaoResult;

@Service
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public TaotaoResult syncContentById(String ContentCid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaotaoResult syncContentByCid(String ContentCid) {
		try {
			Long hdel = jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, ContentCid);
			return TaotaoResult.ok(hdel);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
