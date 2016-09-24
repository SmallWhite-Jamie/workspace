package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.util.TaotaoResult;

public interface ItemService {
	TbItem getItemByid(Long itemId);
	EUDataGridResult getItemList(int page, int rows);
	TaotaoResult createItem(TbItem item, String desc,String itemParams)throws Exception;
}
