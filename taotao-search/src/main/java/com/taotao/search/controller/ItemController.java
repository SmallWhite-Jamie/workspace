package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.service.ItemService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.TaotaoResult;

/**
 * 索引库维护
 * @author lizheng
 * 下午5:21:44
 * @version 1.0
 */
@Controller
@RequestMapping("/manager")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public TaotaoResult importAllItems() {
		
		TaotaoResult result = null;
		try {
			result = itemService.importAllItems();
		} catch (Exception e) {
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
			e.printStackTrace();
		}
		return result;
	}
}
