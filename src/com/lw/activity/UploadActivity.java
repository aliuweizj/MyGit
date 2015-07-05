package com.lw.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lw.http01.R;
import com.lw.thread.UploadHttpThread;

public class UploadActivity extends Activity {
	private Handler handler = new Handler();
	private static final String TAG = "UploadActivity";
	private Button button;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		button = (Button) findViewById(R.id.uploadBtn);
		textView = (TextView) findViewById(R.id.uploadTextView);
		Log.i(TAG, "onCreate");

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory(),
						"liuwei.jpg");
				Log.i(TAG, "照片文件是否存在：" + file);
				new UploadHttpThread(
						"http://192.168.79.102:8080/web3/uploadServlet",
						handler, file, textView).start();
			}
		});
	}
}