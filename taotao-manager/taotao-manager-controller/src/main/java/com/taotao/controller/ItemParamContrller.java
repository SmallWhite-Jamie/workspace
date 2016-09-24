package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/item/param")
public class ItemParamContrller {

	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page,Integer rows){
		
		EUDataGridResult result=itemParamService.getItemParamList(page, rows);
		String s = JsonUtils.objectToJson(result);
		System.out.println(s);
		return result;
	}
	
	/**
	 * 通过id查询规格参数
	 */
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	/**
	 * 保存规格参数
	 * @param cid
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult saveItemParam(@PathVariable Long cid, String paramData){
		
		TbItemParam itemParam =new TbItemParam();
		itemParam.setId(cid);
		itemParam.setParamData(paramData);
		itemParamService.saveItemParam(itemParam);
		
		return TaotaoResult.ok();
	}
}
