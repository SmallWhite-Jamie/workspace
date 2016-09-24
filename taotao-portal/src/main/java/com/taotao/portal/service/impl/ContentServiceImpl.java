package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

/**
 * 
 * @author lizheng
 * 下午7:24:10
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_BAD_URL}")
	private String REST_INDEX_BAD_URL;
	
	@Override
	public String getContentList() throws Exception {
		
		try {
			String jsonData = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_BAD_URL);
			TaotaoResult taotaoresult = TaotaoResult.formatToList(jsonData, TbContent.class);
			//取出内容列表
			List<TbContent> list= (List<TbContent>) taotaoresult.getData();
			//创建符合要求的数据格式
			List<Map> resultList = new ArrayList();
			for (TbContent content : list) {
				Map map = new HashMap();
				map.put("src", content.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", content.getPic2());
				map.put("widthB", 550);
				map.put("alt", content.getSubTitle());
				map.put("href", content.getUrl());
				map.put("heightB", 240);
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
