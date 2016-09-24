package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PicService;
import com.taotao.util.JsonUtils;

/**
 * 上传图片处理
 * @author lizheng
 *
 */
@Controller
public class PicController {
	@Autowired
	private PicService picService;
	
	@RequestMapping("pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile){
		Map result = picService.uploadPicture(uploadFile);
		//为了兼容浏览器(火狐)先把对象转化为字符串，在返回
		String data = JsonUtils.objectToJson(result);
		return data;
	}
}
