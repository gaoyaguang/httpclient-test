package com.light.httpclient.http.inf;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import com.light.httpclient.http.HttpResult;

/**
 * 
 * @Description: HTTP HTTPS Client
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
public interface HttpService {
	
	/**
	 * 
	 * 设置请求客户端实例
	 * 
	 * @param httpclient
	 */
	void setCloseableHttpClient(CloseableHttpClient closeableHttpClient);

	/**
	 * 
	 * 向服务端发送请求的公共方法
	 * 
	 * @param method
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult connect(HttpRequestBase method) throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doGet(String url) throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doGet(String url, Map<String, String> params)
			throws ParseException, ClientProtocolException, IOException, URISyntaxException;

	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doGet(String url, Map<String, String> params, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException, URISyntaxException;

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param json
	 *            JSON格式的请求参数
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, String json) throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param json
	 * @param headers
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, String json, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param parameter
	 * @param contentType
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, String parameter, ContentType contentType)
			throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param parameter
	 * @param headers
	 * @param contentType
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, String parameter, List<Header> headers, ContentType contentType)
			throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, Map<String, String> params)
			throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, Map<String, String> params, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException;

	/**
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @param charset
	 *            取值请参考 {@link org.apache.http.Consts}
	 * @return
	 * @throws ParseException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	HttpResult doPost(String url, Map<String, String> params, List<Header> headers, Charset charset)
			throws ParseException, ClientProtocolException, IOException;
}
