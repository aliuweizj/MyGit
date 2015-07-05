package com.lw.http01;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	private EditText username;
	private EditText password;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		submit = (Button) findViewById(R.id.submit);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// new HttpRegThread(
				// "http://192.168.79.100:8080/testAndroidServer/myServlet",
				// username, password).start();

				// try {
				// new HttpClientThread(
				// "http://192.168.79.100:8080/testAndroidServer/myServlet?username="
				// + URLEncoder.encode(username.getText().toString(),"utf-8")
				// + "&password=" + password.getText())
				// .start();
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				String url = "http://192.168.79.100:8080/testAndroidServer/myServlet";
				new HttpClientThread(url, username.getText().toString(),
						password.getText().toString()).start();
			}
		});
	}
}
