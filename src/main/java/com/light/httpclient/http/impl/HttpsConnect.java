package com.light.httpclient.http.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.light.httpclient.http.HttpResult;
import com.light.httpclient.http.ssl.MySSLConnectionSocketFactory;

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

	@Resource
	private CloseableHttpClient httpsclient;

	/**
	 * 
	 * <p>
	 * 默认取消SSL验证
	 * </p>
	 * <p>
	 * 设置之后会进行SSL验证
	 * </p>
	 * 
	 * @param keyStorePath
	 * @param keyStorepass
	 */
	public void setSSLContext(String keyStorePath, String keyStorepass) {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = MySSLConnectionSocketFactory
				.getSocketFactoryRegistry(custom(keyStorePath, keyStorepass));
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		httpsclient = httpClientBuilder.setConnectionManager(connManager).build();
	}

	public HttpsConnect() {
		super();
		// httpClientBuilder.setConnectionManager(connManager).build();
	}

	/**
	 * 
	 * 请求服务端方法
	 * 
	 * @param method
	 *            请求方式：GET POST DELETE PUT
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult connect(HttpRequestBase method) throws ParseException, ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		try {
			response = httpsclient.execute(method);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
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
