package com.lw.http01;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

public class HttpImage extends Thread {
	private ImageView imageView;
	private String url;
	private Handler handler;

	public HttpImage(ImageView imageView, String url, Handler handler) {
		this.imageView = imageView;
		this.url = url;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			URL httpurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpurl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			final Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					imageView.setImageBitmap(bitmap);
				}
			});
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
