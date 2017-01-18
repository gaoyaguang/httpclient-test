package com.light.httpclient.http.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

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

	public static SSLConnectionSocketFactory getSocketFactory(SSLContext sslContext) {
		return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
	}

	public static SSLContext custom() {
		SSLContext context = null;
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { manager }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
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
	
	/**
	 * 取消SSL检测
	 * @return
	 */
	public static Registry<ConnectionSocketFactory> getSocketFactoryRegistry() {
//		return RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
//				.register("https", new SSLConnectionSocketFactory(custom())).build();
		return RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", getSocketFactory(custom())).build();
	}

	/**
	 * 检测SSL
	 * @param keyStorePath	证书路径
	 * @param keyStorepass	证书秘钥
	 * @return
	 */
	public static Registry<ConnectionSocketFactory> getSocketFactoryRegistry(String keyStorePath, String keyStorepass) {
		return RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", getSocketFactory(custom(keyStorePath, keyStorepass))).build();
	}
}
