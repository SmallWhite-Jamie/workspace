package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.TaotaoResult;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String query, int page) {
		//调用taotao-search的服务(http服务)
		//查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("q", query);
		paramMap.put("page", page+"");
		//paramMap.put("rows", query);
		//调用服务
		TaotaoResult taotaoResult;
		try {
			String jsonData = HttpClientUtil.doGet(SEARCH_BASE_URL,paramMap);
			taotaoResult = TaotaoResult.formatToPojo(jsonData, SearchResult.class);
			if(taotaoResult.getStatus() == 200){
				return (SearchResult) taotaoResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
