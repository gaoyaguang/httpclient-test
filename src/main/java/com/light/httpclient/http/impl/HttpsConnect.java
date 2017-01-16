package com.light.httpclient.http.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.light.httpclient.http.ssl.MySSLConnectionSocketFactory;
import com.light.httpclient.util.PropertiesUtil;

/**
 * 
 * @Description: HTTPS 实现类
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

	@Resource
	private HttpClientBuilder httpClientBuilder;

	private static Integer maxTotal;

	private static Integer defaultMaxPerRoute;
	
	public HttpsConnect() {
		super();
	}
	
	public HttpsConnect(CloseableHttpClient closeableHttpClient) {
		super();
		this.setCloseableHttpClient(closeableHttpClient);
	}
	
	@Override
	@Autowired
	public void setCloseableHttpClient(@Qualifier("httpsclient")CloseableHttpClient closeableHttpClient) {
		this.closeableHttpClient = closeableHttpClient;
	}

	static {
		String path = "/init.properties";
		String maxTotals = PropertiesUtil.getValue(path, "httpclient.maxTotal");
		maxTotal = StringUtils.isBlank(maxTotals) ? 1000 : Integer.parseInt(maxTotals);
		String defaultMaxPerRoutes = PropertiesUtil.getValue(path, "httpclient.maxTotal");
		defaultMaxPerRoute = StringUtils.isBlank(defaultMaxPerRoutes) ? 300 : Integer.parseInt(defaultMaxPerRoutes);
	}

	/**
	 * 
	 * <p>
	 * 	设置证书后会验证证书的合法性
	 * </p>
	 * <p>
	 * 	为了防止线程安全问题，设置后重新实例化一个对象
	 * </p>
	 * 
	 * @param keyStorePath	证书存放路径
	 * @param keyStorepass	证书密码
	 * @return HttpsConnect 新对象
	 */
	public HttpsConnect setSSLContext(String keyStorePath, String keyStorepass) {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = MySSLConnectionSocketFactory
				.getSocketFactoryRegistry(custom(keyStorePath, keyStorepass));
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		connManager.setMaxTotal(maxTotal);
		return new HttpsConnect(httpClientBuilder.setConnectionManager(connManager).build());
	}

	public static SSLContext custom(String keyStorePath, String keyStorepass) {
		SSLContext sc = null;
		FileInputStream instream = null;
		KeyStore trustStore = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			instream = new FileInputStream(new File(keyStorePath));
			trustStore.load(instream, keyStorepass.toCharArray());
			// 相信自己的CA和所有自签名的证书
			sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sc;
	}
}
