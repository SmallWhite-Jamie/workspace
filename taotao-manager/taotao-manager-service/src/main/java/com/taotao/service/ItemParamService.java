package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.util.TaotaoResult;

public interface ItemParamService {
	public TaotaoResult getItemParamByCid(Long CId);
	public TaotaoResult saveItemParam(TbItemParam itemParam);
	public EUDataGridResult getItemParamList(int page, int rows);
}
