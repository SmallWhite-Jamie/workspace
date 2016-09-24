package com.totao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.util.FtpUtil;

public class FTPTest {
	
	@Test
	public void ftpTest()throws Exception{
		//创建一个ftp客户端对象
		FTPClient ftpClient = new FTPClient();
		//创建ftp链接
		ftpClient.connect("192.168.146.128", 21);
		//登录ftp服务器
		ftpClient.login("ftpuser", "ftpuser");
		//设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//设置上传文件的格式
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		//上传文件
		InputStream local = new FileInputStream(new File("D:\\bizhi.jpg"));
		ftpClient.storeFile("bizhi.jpg", local);
		//关闭连接
		ftpClient.logout();
	}
	
	@Test
	public void ftpUtilTest() throws Exception{
		FtpUtil.uploadFile("192.168.146.128", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", 
				"2016/06/28", "hello.jpg", new FileInputStream(new File("D:\\work\\gou.jpg")));
	}
}
