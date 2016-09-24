package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

/**
 * 查询商信息
 * 
 * @author 李峥 2016年7月13日 下午4:38:41
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_BASE_KEY}")
	private String REDIS_ITEM_BASE_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	@Value("${REDIS_ITEM_DESC_KEY}")
	private String REDIS_ITEM_DESC_KEY;
	@Value("${REDIS_ITEM_PARAM_KEY}")
	private String REDIS_ITEM_PARAM_KEY;

	/**
	 * 商品基础信息查询
	 */
	public TaotaoResult getItemBaseInfo(Long itemId) {
		// 缓存中查找
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + itemId + REDIS_ITEM_BASE_KEY);
			if (!StringUtils.isBlank(json)) {
				return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbItem.class));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 写入缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + itemId + REDIS_ITEM_BASE_KEY, JsonUtils.objectToJson(item));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + itemId + REDIS_ITEM_BASE_KEY, REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDescInfo(Long itemId) {

		// redis缓存中取
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + itemId + REDIS_ITEM_DESC_KEY);
			if (!StringUtils.isBlank(json)) {
				return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbItemDesc.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		// 存入redis缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + itemId + REDIS_ITEM_DESC_KEY, JsonUtils.objectToJson(itemDesc));
			// 设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY + itemId + REDIS_ITEM_DESC_KEY, REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParamInfo(Long itemId) {

		// redis缓存中取
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + itemId + REDIS_ITEM_PARAM_KEY);
			if (!StringUtils.isBlank(json)) {
				return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbItemParamItem.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			TbItemParamItem paramItem = list.get(0);
			// 存入redis缓存
			try {
				jedisClient.set(REDIS_ITEM_KEY + itemId + REDIS_ITEM_PARAM_KEY, JsonUtils.objectToJson(paramItem));
				// 设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + itemId + REDIS_ITEM_PARAM_KEY, REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return TaotaoResult.ok(paramItem);
		}

		return TaotaoResult.build(400, "无此商品规格信息");
	}
}
