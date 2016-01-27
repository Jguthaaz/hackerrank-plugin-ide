package com.koldbyte.hackerrank.plugin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RequestWrapper {
	BasicCookieStore cookieStore = new BasicCookieStore();

	RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();

	CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
			.setConnectionManagerShared(true).setDefaultRequestConfig(globalConfig).build();
	String csrf = "";

	public String get(String url) throws IOException {
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(globalConfig);
			if (!csrf.isEmpty()) {
				httpget.setHeader("X-CSRF-Token", csrf);
			}

			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();
				String response = readString(entity.getContent());

				return response;
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public String post(String url, String payload) throws IOException {
		try {
			StringEntity requestEntity = new StringEntity(payload, ContentType.APPLICATION_JSON);
			HttpPost postMethod = new HttpPost(url);

			postMethod.setConfig(globalConfig);

			if (!csrf.isEmpty()) {
				postMethod.setHeader("X-CSRF-Token", csrf);
			}
			postMethod.setEntity(requestEntity);
			CloseableHttpResponse response1 = httpclient.execute(postMethod);
			try {
				HttpEntity entity = response1.getEntity();
				String response = readString(entity.getContent());

				return response;
			} finally {
				response1.close();
			}
		} finally {
			httpclient.close();
		}
	}

	public String readString(InputStream in) throws IOException {
		InputStreamReader is = new InputStreamReader(in);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();

		while (read != null) {
			// System.out.println(read);
			sb.append(read);
			read = br.readLine();

		}

		return sb.toString();
	}

	public String getCsrf() {
		return csrf;
	}

	public void setCsrf(String csrf) {
		this.csrf = csrf;
	}

	public RequestWrapper() {
		super();
	}
}
