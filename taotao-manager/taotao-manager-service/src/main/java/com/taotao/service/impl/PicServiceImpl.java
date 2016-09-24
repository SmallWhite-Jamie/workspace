package com.taotao.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PicService;
import com.taotao.util.FtpUtil;
import com.taotao.util.IDUtils;

/**
 * 图片上传服务
 * @author lizheng
 *
 */
@Service
public class PicServiceImpl implements PicService {
	
	//从配置文件中加载
	
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	
	@Value("${ETP_PORT}")
	private Integer ETP_PORT;
	
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile multipartFile) {
		
		Map resultMap = new HashMap<>();
		
		//取得原来文件名
		String oldName = multipartFile.getOriginalFilename();
		//生成新的文件名
		//UUID newName = UUID.randomUUID();文件名太常也可以使用下面的这种方式
		String newName = IDUtils.genImageName();
		newName = newName + oldName.substring(oldName.lastIndexOf("."));
		try {
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, ETP_PORT, FTP_USERNAME, FTP_PASSWORD,
					FTP_BASE_PATH, imagePath, newName,
					multipartFile.getInputStream());
			if(result){
				resultMap.put("error", 0);
				resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
				return resultMap;
			}else{
				resultMap.put("error", 1);
				resultMap.put("message", "上传失败");
				return resultMap;
			}
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "上传失败,出现异常");
			e.printStackTrace();
			return resultMap;
		}
	}

}
