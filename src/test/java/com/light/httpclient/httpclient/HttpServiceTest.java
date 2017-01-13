package com.light.httpclient.httpclient;

import java.net.SocketTimeoutException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.light.httpclient.http.impl.HttpConnect;
import com.light.httpclient.http.impl.HttpsConnect;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class HttpServiceTest {
	
	@Resource
	private HttpConnect httpConnect;
	
	@Resource
	private HttpsConnect httpsConnect;
	
	@Test
	public void testDoGet() {
		
		String url = "https://sso.wsria.com:8443/blog/rest/page/login";
//		String url = "https://kyfw.12306.cn/otn/";
//		String url = "http://localhost:8080/traffic-api/test";
		try {
			System.out.println("=================httpConnect测试结果===================");
			String response = httpConnect.doGet(url).getData();
			System.out.println("response= " + response);
			
		} catch (SocketTimeoutException e) {
			System.out.println("超时异常：");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("其他异常：");
			System.out.println(e);
		}
		
		try {
			System.out.println("=================httpsConnect测试结果===================");
//			httpsConnect.setSSLContext("D:/keys/wsriakey", "123456");
			String response = httpsConnect.doGet(url).getData();
			System.out.println("response= " + response);
			
		} catch (SocketTimeoutException e) {
			System.out.println("超时异常：");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("其他异常：");
			System.out.println(e);
		}
	}
	
//	@Test
//	public void testDoPost() {
//		try {
//			String url = "http://localhost:8080/traffic-api/orderFlowService";
//			String request = "{\"serialNumber\":\"Test20161216160023\",\"mobile\":\"eca84a651d668132b3aa8dfe7e8071b9\",\"flowno\":\"dbdc380273af72ea\",\"merchantNo\":\"6b3932ab201e9f1e\",\"source\":\"ae9d0a3edffc9842\",\"token\":\"a700178e35342de8\",\"sign\":\"60737b75bf7c80a6b63a337446d074a42303470c40e860c46bc2e8773dc2c1a37e8cf35b9a8649040edad8a5e52273eb113ccb941d2efa737a247c25c5911d0ddb20a0c93ad38f9f40798f082c5f91adffef15e24cf8778471f17fda22d9c06ef2628b1471b2b5212dbfb1a3f8bcc67fcdec7db568efb6d7190d8d7e5b9b230ae6f5a720a741bd3fa00d1f5c3badbf12\"}";
//			String response = httpsConnect.doPost(url, request).getData();
//			System.err.println(response);
//		} catch (SocketTimeoutException e) {
//			System.err.println("超时异常：" + e);
//		} catch (Exception e) {
//			System.err.println("其他异常：" + e);
//		}
//	}
}
