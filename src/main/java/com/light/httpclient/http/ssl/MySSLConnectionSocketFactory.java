package com.light.httpclient.http.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

public class MySSLConnectionSocketFactory {

	/** 取消检测SSL **/
	private static TrustManager manager = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
	};

	public static SSLConnectionSocketFactory getSocketFactory() {
		return new SSLConnectionSocketFactory(getSSLContext(), NoopHostnameVerifier.INSTANCE);
	}

	public static SSLContext getSSLContext() {
		SSLContext context = null;
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { manager }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}
}
