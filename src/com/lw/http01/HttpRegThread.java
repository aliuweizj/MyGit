package com.lw.http01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import android.widget.EditText;

public class HttpRegThread extends Thread {

	private String url;
	private EditText username;
	private EditText password;

	@Override
	public void run() {
		//显示手机操作系统的参数
//		Properties property = System.getProperties();
//		property.list(System.out);
		
		try {
			url = url +"?username="+URLEncoder.encode(username.getText().toString(), "utf-8")+"&password="+password.getText();
			URL httpurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str = "";
			while((str = reader.readLine())!=null){
				sb.append(str);
			}
			
			System.out.println(sb.toString());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HttpRegThread(String url, EditText username, EditText password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

}
