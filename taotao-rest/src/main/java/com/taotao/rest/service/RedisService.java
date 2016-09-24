package com.taotao.rest.service;

import com.taotao.util.TaotaoResult;

public interface RedisService {
	TaotaoResult syncContentById(String ContentCid);
	TaotaoResult syncContentByCid(String ContentCid);
}
