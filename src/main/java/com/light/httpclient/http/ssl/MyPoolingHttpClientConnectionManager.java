package com.light.httpclient.http.ssl;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

@ThreadSafe
public class MyPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {
	
	private static Registry<ConnectionSocketFactory> getDefaultRegistry() {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", MySSLConnectionSocketFactory.getSocketFactory())
                .build();
    }

	public MyPoolingHttpClientConnectionManager() {
		super(getDefaultRegistry());
	}
}
