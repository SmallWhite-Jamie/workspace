package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatSevice;
import com.taotao.util.JsonUtils;


@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatSevice itemCatSevice;
	
	//produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"乱码解决方式
	//
//	@RequestMapping(value="itemcat/list",
//			produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//	@ResponseBody
//	public String getItemCatList(String callback){
//		//获取数据对象
//		CatResult catResult = itemCatSevice.getItemList();
//		//转换为字符串
//		String json = JsonUtils.objectToJson(catResult);
//		//拼装返回值
//		String result = callback + "(" + json + ");"; 
//		
//		return result;
//	}
	
	/*
	 * 4.1之后可以使用此方法
	 */
	@RequestMapping(value="itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback){
		//获取数据对象
		CatResult catResult = itemCatSevice.getItemList();
		MappingJacksonValue jacksonValue =new MappingJacksonValue(catResult);
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}
}
