package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.service.RedisService;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/cacheSync")
public class RedisController {
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/{syncByContentCid}")
	@ResponseBody
	public TaotaoResult syncByContentCid(@PathVariable String syncByContentCid){
		TaotaoResult result = redisService.syncContentByCid(syncByContentCid);
		return result;
	}
	
	
}
