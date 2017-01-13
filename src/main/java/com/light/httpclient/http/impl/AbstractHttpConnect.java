package com.light.httpclient.http.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.light.httpclient.http.HttpResult;
import com.light.httpclient.http.inf.HttpService;

public abstract class AbstractHttpConnect implements HttpService {

	@Override
	public abstract HttpResult connect(HttpRequestBase method)
			throws ParseException, ClientProtocolException, IOException;

	@Override
	public HttpResult doGet(String url) throws ParseException, ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpResult result = this.connect(httpGet);
		return result;
	}

	@Override
	public HttpResult doGet(String url, Map<String, String> params)
			throws ParseException, ClientProtocolException, IOException, URISyntaxException {
		HttpGet httpGet = new HttpGet(this.getUrl(url, params));
		return this.connect(httpGet);
	}

	@Override
	public HttpResult doGet(String url, Map<String, String> params, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException, URISyntaxException {
		HttpGet httpGet = new HttpGet(this.getUrl(url, params));
		if (headers != null) {
			for (Header header : headers) {
				httpGet.setHeader(header);
			}
		}
		return this.connect(httpGet);
	}

	private String getUrl(String url, Map<String, String> params) throws URISyntaxException {
		if (params != null) {
			URIBuilder uriBuilder = new URIBuilder(url);
			for (String key : params.keySet()) {
				uriBuilder.addParameter(key, params.get(key));
			}
			return uriBuilder.build().toString();
		}
		return url;
	}

	@Override
	public HttpResult doPost(String url, String json) throws ParseException, ClientProtocolException, IOException {
		List<Header> headers = new ArrayList<Header>();
		return this.doPost(url, json, headers);
	}

	@Override
	public HttpResult doPost(String url, String json, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException {
		if (url == null || json == null) {
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		if (headers != null) {
			for (Header header : headers) {
				httpPost.setHeader(header);
			}
		}
		return this.connect(httpPost);
	}

	@Override
	public HttpResult doPost(String url, String parameter, ContentType contentType)
			throws ParseException, ClientProtocolException, IOException {
		return this.doPost(url, parameter, null, contentType);
	}

	@Override
	public HttpResult doPost(String url, String parameter, List<Header> headers, ContentType contentType)
			throws ParseException, ClientProtocolException, IOException {
		if (url == null || parameter == null) {
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(parameter, contentType));
		if (headers != null) {
			for (Header header : headers) {
				httpPost.setHeader(header);
			}
		}
		return this.connect(httpPost);
	}

	@Override
	public HttpResult doPost(String url, Map<String, String> params)
			throws ParseException, ClientProtocolException, IOException {
		return this.doPost(url, params, null);
	}

	@Override
	public HttpResult doPost(String url, Map<String, String> params, List<Header> headers)
			throws ParseException, ClientProtocolException, IOException {
		return this.doPost(url, params, headers, null);
	}

	@Override
	public HttpResult doPost(String url, Map<String, String> params, List<Header> headers, Charset charset)
			throws ParseException, ClientProtocolException, IOException {
		if (url == null || params == null) {
			return null;
		}
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			parameters.add(new BasicNameValuePair(key, params.get(key)));
		}
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, charset.name());
		httpPost.setEntity(formEntity);
		if (headers != null) {
			for (Header header : headers) {
				httpPost.setHeader(header);
			}
		}
		return this.connect(httpPost);
	}
}
