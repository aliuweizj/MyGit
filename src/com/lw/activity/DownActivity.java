package com.lw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lw.http01.R;
import com.lw.thread.DownThread;

public class DownActivity extends Activity {
	private Button button;
	private TextView textView;
	private int count = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int result = msg.what;
			count += result;
			if (count == 3) {
				textView.setText("DownLoad Success!");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.down);
		button = (Button) findViewById(R.id.button1);
		textView = (TextView) findViewById(R.id.downTextView);

		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//在主线程重新启线程，避免4.x以上不允许主线程启动网络访问的毛病
				new Thread(){
					@Override
					public void run(){
						DownThread download = new DownThread(handler);
						download.downloadFile("http://192.168.79.102:8080/testAndroidServer/liuwei.jpg");
					}
				}.start();
			}
		});
	}
}
