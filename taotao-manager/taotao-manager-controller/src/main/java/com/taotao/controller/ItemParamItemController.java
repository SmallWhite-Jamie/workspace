package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.filter.AutoLoad;
import com.taotao.service.ItemParamItemService;

/**
 * 展示规格参数
 * 
 * @author lizheng
 *
 */

@Controller
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showItem/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model){
		model.addAttribute("itemParam", itemParamItemService.getItemParamItem(itemId));
		return "item";
	}
}
