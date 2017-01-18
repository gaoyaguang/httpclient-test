package com.light.httpclient.http.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.light.httpclient.http.ssl.MySSLConnectionSocketFactory;
import com.light.httpclient.util.PropertiesUtil;

/**
 * 
 * @Description: HTTPS 实现类
 *               <p>
 *               线程不安全的
 *               </p>
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
@Service("httpsConnect")
public class HttpsConnect extends AbstractHttpConnect {

	private static final Logger logger = LoggerFactory.getLogger(HttpsConnect.class);

	@Resource
	private HttpClientBuilder httpClientBuilder;

	/** 只在检查SSL时用到 */
	private Integer maxTotal;
	/** 只在检查SSL时用到 */
	private Integer defaultMaxPerRoute;

	private String keyStorePath;

	private String keyStorepass;

	@Override
	@Autowired
	public void setCloseableHttpClient(@Qualifier("httpsclient") CloseableHttpClient closeableHttpClient) {
		this.closeableHttpClient = closeableHttpClient;
	}

	@PostConstruct
	public void init() {
		try {
			String path = "/init.properties";
			String maxTotals = PropertiesUtil.getValue(path, "httpclient.maxTotal");
			if (!StringUtils.isBlank(maxTotals)) {
				this.setMaxTotal(Integer.parseInt(maxTotals));
			}
			String defaultMaxPerRoutes = PropertiesUtil.getValue(path, "httpclient.maxTotal");
			if (!StringUtils.isBlank(defaultMaxPerRoutes)) {
				this.setDefaultMaxPerRoute(Integer.parseInt(defaultMaxPerRoutes));
			}
		} catch (Exception e) {
			logger.error("初始化http配置信息异常：{}", e.getMessage());
		}
	}

	/**
	 * <p>
	 * 注册带证书请求客户端
	 * </p>
	 */
	public void registry() {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = MySSLConnectionSocketFactory
				.getSocketFactoryRegistry(keyStorePath, keyStorepass);
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		connectionManager.setMaxTotal(maxTotal);
		new IdleConnectionEvictor(connectionManager, 10000, TimeUnit.MILLISECONDS).start();
		this.setCloseableHttpClient(httpClientBuilder.setConnectionManager(connectionManager).build());
	}

	public Integer getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	/**
	 * 仅在检查SSL的情况下起作用
	 * 
	 * @param maxTotal
	 */
	public HttpsConnect setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
		return this;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	/**
	 * 仅在检查SSL的情况下起作用
	 * 
	 * @param maxTotal
	 */
	public HttpsConnect setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
		return this;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public HttpsConnect setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
		return this;
	}

	public String getKeyStorepass() {
		return keyStorepass;
	}

	public HttpsConnect setKeyStorepass(String keyStorepass) {
		this.keyStorepass = keyStorepass;
		return this;
	}
}
