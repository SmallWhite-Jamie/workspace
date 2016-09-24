package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
import com.taotao.util.TaotaoResult;

/**
 * 商品的规格参数模板
 * 
 * @author lizheng
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getItemParamByCid(Long CId) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(CId);
		List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
		if (itemParams != null && itemParams.size() > 0) {
			return TaotaoResult.ok(itemParams.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult saveItemParam(TbItemParam itemParam) {
		// 补全
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		// 插入
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		TbItemParamExample example = new TbItemParamExample();
		// 分页
		PageHelper.startPage(page, rows);

		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 取得总记录数
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);
		// 封装返回
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

}
