package com.lw.http01;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class Http_01Activity extends Activity {
	/** Called when the activity is first created. */
	private WebView webView;
	private ImageView imageView;
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = (WebView) findViewById(R.id.webView1);
		imageView = (ImageView) findViewById(R.id.imageView1);

		// new HttpThread("http://www.baidu.com", webView, handler).start();
		new HttpThread(
				"http://g.hiphotos.baidu.com/image/pic/item/d439b6003af33a87a7693ecbc25c10385243b5de.jpg",
				imageView, handler).start();
	}
}