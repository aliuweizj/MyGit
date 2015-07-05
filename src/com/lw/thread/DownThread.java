package com.lw.thread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class DownThread extends Thread {

	private Handler handler;
	// private TextView textView;

	// 创建线程池
	private Executor executorPool = Executors.newFixedThreadPool(3);

	public DownThread(Handler handler) {
		this.handler = handler;
	}

	static class DownLoadRunnable implements Runnable {
		private String url;
		private String fileName;
		private long start;
		private long end;
		private Handler handler;

		public DownLoadRunnable(String url, String fileName, long start,
				long end, Handler handler) {
			this.url = url;
			this.fileName = fileName;
			this.start = start;
			this.end = end;
			this.handler = handler;
		}

		@Override
		public void run() {
			URL httpurl;
			try {
				httpurl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) httpurl
						.openConnection();
				conn.setReadTimeout(5000);
				conn.setRequestMethod("GET");

				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
				RandomAccessFile access = new RandomAccessFile(new File(
						fileName), "rwd");
				access.seek(start);
				InputStream in = conn.getInputStream();
				byte[] b = new byte[2 * 1024];
				int len = 0;
				while ((len = in.read(b)) != -1) {
					access.write(b);
				}

				if (access != null) {
					access.close();
				}
				if (in != null) {
					in.close();
				}
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void downloadFile(String url) {
		try {
			URL httpurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");

			int count = conn.getContentLength();
			int block = count / 3;

			String fileName = getFileName(url);
			File parent = Environment.getExternalStorageDirectory();
			File fileDownLoad = new File(parent, fileName);

			for (int i = 0; i < 3; i++) {
				long start = i * block;
				long end = (i + 1) * block - 1;
				if (i == 2) {
					end = count;
				}
				DownLoadRunnable runnable = new DownLoadRunnable(url,
						fileDownLoad.getAbsolutePath(), start, end, handler);
				executorPool.execute(runnable);

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
	}

	public String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

}
