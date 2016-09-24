package com.taotao.rest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatSevice;
import com.taotao.util.JsonUtils;

@Service
public class ItemCatServiceImpl implements ItemCatSevice {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CAT_REDIS_KEY}")
	private String INDEX_CAT_REDIS_KEY;

	@Override
	public CatResult getItemList() {
		CatResult catResult = new CatResult();
		// 从缓存中获取
		String string = jedisClient.get(INDEX_CAT_REDIS_KEY);
		if (!StringUtils.isBlank(string)) {
			try {
				List<Object> data = JsonUtils.jsonToList(string, Object.class);
				catResult.setData(data);
				return catResult;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<Object> catList = getCatList(0);
		catResult.setData(catList);
		// 存入redis缓存
		try {
			String result = JsonUtils.objectToJson(catResult);
			jedisClient.set(INDEX_CAT_REDIS_KEY, result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return catResult;
	}

	/**
	 * 递归实现数据查询
	 * 
	 * @param parentId
	 * @return
	 */

	private List<Object> getCatList(long parentId) {

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List catNodeList = new ArrayList();
		int count = 0;
		for (TbItemCat itemCat : list) {
			// 判断是否是父节点
			if (itemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				} else {
					catNode.setName(itemCat.getName());

				}
				catNode.setUrl("/products/" + itemCat.getId() + ".html");
				catNode.setItem(getCatList(itemCat.getId()));
				catNodeList.add(catNode);
				count++;
				if (count >= 14) {
					break;
				}
			} else {// 叶子节点
				catNodeList.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
			}
		}
		return catNodeList;

	}
}
