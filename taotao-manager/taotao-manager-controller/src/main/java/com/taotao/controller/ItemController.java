package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.util.TaotaoResult;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 通过id查询商品
	 * @param itemId
	 * @return
	 */
	@RequestMapping("item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem item = itemService.getItemByid(itemId);
		return item;
	}
	
	/**
	 * 分页查询商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page,Integer rows){
		EUDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}
	
	/**
	 * 保存商品
	 * @param item
	 * @param desc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value=("/item/save"),method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc,String itemParams) throws Exception{
		TaotaoResult taotaoResult = itemService.createItem(item,desc,itemParams);
		return taotaoResult;
	}
	
}
