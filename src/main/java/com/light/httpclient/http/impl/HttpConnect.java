package com.light.httpclient.http.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.light.httpclient.http.HttpResult;

/**
 * 
 * @Description: HTTP 实现类
 *
 * @author GaoYaguang
 * @version 1.0.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2017年1月13日     GaoYaguang    1.0.0     1.0.0 Version
 *          </pre>
 */
@Service("httpConnect")
public class HttpConnect extends AbstractHttpConnect {

	@Resource
	private CloseableHttpClient httpclient;
	
	/**
	 * 
	 * 请求服务端方法
	 * 
	 * @param method	请求方式：GET POST DELETE PUT
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult connect(HttpRequestBase method) throws ParseException, ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(method);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
}
