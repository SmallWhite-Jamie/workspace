package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.service.ItemService;
import com.taotao.util.TaotaoResult;

/**
 * 商品信息controller
 * @author 李峥
 * 2016年7月13日 下午4:44:19
 * @version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 商品基础信息
	 * @param itemId
	 * @return TaotaoResult
	 */
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId){
		
		TaotaoResult result = itemService.getItemBaseInfo(itemId);
		
		return result;
	}
	
	/**
	 * 商品描述信息
	 * @param itemId
	 * @return TaotaoResult
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescInfo(@PathVariable Long itemId){
		
		TaotaoResult result = itemService.getItemDescInfo(itemId);
		
		return result;
	}
	
	/**
	 * 商品规格信息
	 * @param itemId
	 * @return TaotaoResult
	 */
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamInfo(@PathVariable Long itemId){
		
		TaotaoResult result = itemService.getItemParamInfo(itemId);
		
		return result;
	}
}
