package com.lw.http01;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientThread extends Thread {
	
	private String url;
	private String username;
	private String password;
	
	
	public HttpClientThread(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public HttpClientThread(String url) {
		this.url = url;
	}
	
	private void doHttpClientPost(){
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpClient httpClient = new DefaultHttpClient();
			List<NameValuePair> reqList = new ArrayList<NameValuePair>();
			reqList.add(new BasicNameValuePair("username",username));
			reqList.add(new BasicNameValuePair("password",password));
			httpPost.setEntity(new UrlEncodedFormEntity(reqList,HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String content = EntityUtils.toString(response.getEntity());
				
				System.out.println(content);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doHttpClientGet(){
		HttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClent = new DefaultHttpClient();
			response = httpClent.execute(httpGet);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String content = EntityUtils.toString(response.getEntity());
				System.out.println(content+"-----------------");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
//		doHttpClientGet();
		
		doHttpClientPost();
	}
	
}
