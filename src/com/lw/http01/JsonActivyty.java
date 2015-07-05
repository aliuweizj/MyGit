package com.lw.http01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class JsonActivyty extends Activity {

	private ListView listView;
	private JsonAdapter jsonAdapter;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json);
		listView = (ListView) findViewById(R.id.listView);
		jsonAdapter = new JsonAdapter(this);
		new JsonThread("http://192.168.79.101:8080/testAndroidServer/jsonServlet", this, listView, jsonAdapter, handler).start();
	}
}
