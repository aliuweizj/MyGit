package com.lw.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Handler;
import android.widget.TextView;

import com.lw.bean.Girl;

public class XmlThread extends Thread {
	private String url;
	private TextView textView ;
	private Handler handler;
	
	

	public XmlThread(String url, TextView textView, Handler handler) {
		this.url = url;
		this.textView = textView;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			URL httpurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpurl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			
			InputStream in = conn.getInputStream();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			
			parser.setInput(in,"UTF-8");
			int eventType = parser.getEventType();
			final List<Girl> list = new ArrayList<Girl>();
			Girl girl = null;
			while(eventType!=XmlPullParser.END_DOCUMENT){
				String data = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if("girl".equals(data)){
						girl = new Girl();
					}
					if("name".equals(data)){
						girl.setName(parser.nextText());
					}
					if("age".equals(data)){
						girl.setAge(Integer.parseInt(parser.nextText()));
					}
					if("school".equals(data)){
						girl.setSchool(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if("girl".equals(data) && girl != null){
						list.add(girl);
					}
					break;
				}
				eventType = parser.next();//手动向下移动
			}
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					textView.setText(list.toString());
				}
			});
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
