package com.light.httpclient.http.impl;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
	
	@Override
	@Autowired
	public void setCloseableHttpClient(@Qualifier("httpclient")CloseableHttpClient closeableHttpClient) {
		this.closeableHttpClient = closeableHttpClient;
	}
}
