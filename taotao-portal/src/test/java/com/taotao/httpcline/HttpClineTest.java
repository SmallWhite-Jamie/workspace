package com.taotao.httpcline;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClineTest {
	
	@Test
	public void doGet() throws Exception{
		//创建一个httpCline对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建一个get对象
		HttpGet get =new HttpGet("http://www.baidu.com");
		//执行请求
		CloseableHttpResponse response = client.execute(get);
		//取出相应的结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity,"utf-8");
		System.out.println(string);
		//关闭一个httpCline
		client.close();
	}
	@Test
	public void doGetWitchParam()throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=monline_3_dg&");
		builder.addParameter("wd", "电视");
		HttpGet get = new HttpGet(builder.build());
		CloseableHttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		client.close();
	}
	
	@Test
	public void doPost() throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8082/testPost.html");
		CloseableHttpResponse execute = client.execute(httpPost);
		HttpEntity entity = execute.getEntity();
		String string = EntityUtils.toString(entity);
		System.err.println(string);
	}
	@Test
	public void doPostWithParam() throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8082/testPost.html");
		
		//创建一个entity对象模拟表单
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name", "li"));
		StringEntity entity = new UrlEncodedFormEntity(list);
		httpPost.setEntity(entity);
		
		CloseableHttpResponse execute = client.execute(httpPost);
		HttpEntity entity2 = execute.getEntity();
		String string = EntityUtils.toString(entity2);
		System.err.println(string);
	}
}
