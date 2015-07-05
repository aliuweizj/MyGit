package com.lw.http01;

import com.lw.xml.XmlThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class XmlActivity extends Activity {
	
	private TextView textView ;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parsexml);
		textView = (TextView)findViewById(R.id.xmlTextView);
		new XmlThread("http://192.168.79.101:8080/testAndroidServer/girl.xml", textView, handler).start(); 
	}
	
}
