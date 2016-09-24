package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import com.taotao.util.IDUtils;
import com.taotao.util.TaotaoResult;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper descMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	/**
	 * 通过id查询商品
	 */
	public TbItem getItemByid(Long itemId) {
//		itemMapper.selectByPrimaryKey(itemId);
		TbItemExample itemExample = new TbItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> items = itemMapper.selectByExample(itemExample);
		if(items!=null && items.size()>0){
			return items.get(0);
		}
		return null;
	}
	
	/**
	 * 商品列表分页查询
	 */
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页
		PageHelper.startPage(page, rows);
		//返回一页信息
		List<TbItem> list = itemMapper.selectByExample(example);
		//取得总记录数
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//封装返回
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 保存商品信息
	 */
	@Override
	public TaotaoResult createItem(TbItem item, String desc,String itemParams) throws Exception {
		//补全item
		//生成商品id
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//设置status商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		//设置创建时间
		item.setCreated(new Date());
		//设置更新时间
		item.setUpdated(new Date());
		//插入数据库
		itemMapper.insert(item);
		//添加详情描述
		TaotaoResult result = saveItemDesc(itemId,desc);
		//添加规格参数
		TaotaoResult paramResult = saveItemParamItem(itemId, itemParams);
		if(result.getStatus()!=200){
			throw new Exception();
		}
		if(paramResult.getStatus()!=200){
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	/**
	 * 保存商品的描述信息
	 * @return
	 */
	private TaotaoResult saveItemDesc(Long id, String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		descMapper.insert(itemDesc);
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 保存商品规格信息
	 * @param itemid 商品id
	 * @param itemParams 商品规格信息
	 * @return
	 */
	private TaotaoResult saveItemParamItem(Long itemid, String itemParams){
		
		TbItemParamItem itemParam = new TbItemParamItem();
		itemParam.setItemId(itemid);
		itemParam.setParamData(itemParams);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		
		itemParamItemMapper.insert(itemParam);
		
		return TaotaoResult.ok();
	}
}
