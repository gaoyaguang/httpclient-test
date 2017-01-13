package com.light.httpclient.http.ssl;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

@ThreadSafe
public class MyPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {

	private static Registry<ConnectionSocketFactory> getDefaultRegistry() {
		return MySSLConnectionSocketFactory.getSocketFactoryRegistry(MySSLConnectionSocketFactory.getSSLContext());
	}

	public MyPoolingHttpClientConnectionManager() {
		super(getDefaultRegistry());
	}

	public MyPoolingHttpClientConnectionManager(final Registry<ConnectionSocketFactory> socketFactoryRegistry) {
		super(socketFactoryRegistry, null, null);
	}
}
