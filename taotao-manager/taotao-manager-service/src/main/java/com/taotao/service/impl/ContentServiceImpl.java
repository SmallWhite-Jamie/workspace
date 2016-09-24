package com.taotao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.TaotaoResult;


@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_SYNC_CONTENT}")
	private String REST_SYNC_CONTENT;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全对象内容
		try {
			content.setCreated(new Date());
			content.setUpdated(new Date());
			contentMapper.insert(content);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.ok("Exception");
		}
		//添加缓存逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_SYNC_CONTENT+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("同步数据失败");
		}
		
		return TaotaoResult.ok();
	}

}
