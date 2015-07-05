package com.lw.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.lw.bean.FormFile;

/**
 * �ϴ��ļ���������
 * 
 * @author Administrator
 * 
 */
public class UploadHttpThread extends Thread {

	private static final String TAG = "UploadActivity";
	private String url;
	private Handler handler;
	private File file;
	private TextView textView;


	public UploadHttpThread(String url, Handler handler, File file,
			TextView textView) {
		this.url = url;
		this.handler = handler;
		this.file = file;
		this.textView = textView;
	}

	/**
	 * �ϴ�ͼƬ��������
	 * 
	 * @param imageFile
	 *            ����·��
	 */
	public void uploadFile(File imageFile) {
		Log.i(TAG, "upload start");
		try {
			String requestUrl = url;
			// ������ͨ��Ϣ
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", "����");
			params.put("pwd", "zhangsan");
			params.put("age", "21");
			params.put("fileName", imageFile.getName());
			// �ϴ��ļ�
			FormFile formfile = new FormFile(imageFile.getName(), imageFile,
					"image", "application/octet-stream");

			post(requestUrl, params, formfile);
			Log.i(TAG, "upload success");
		} catch (Exception e) {
			Log.i(TAG, "upload error");
			e.printStackTrace();
		}
		Log.i(TAG, "upload end");
	}

	public static boolean post(String path, Map<String, String> params,
			FormFile[] files) throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // ���ݷָ���
		final String endline = "--" + BOUNDARY + "--\r\n";// ���ݽ�����־

		int fileDataLength = 0;
		for (FormFile uploadFile : files) {// �õ��ļ��������ݵ��ܳ���
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null) {
				fileDataLength += uploadFile.getFile().length();
			} else {
				fileDataLength += uploadFile.getData().length;
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// �����ı����Ͳ�����ʵ������
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// ���㴫�����������ʵ�������ܳ���
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(path);
		int port = url.getPort() == -1 ? 8080 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// �������HTTP����ͷ�ķ���
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// д��HTTP����ͷ�����HTTPЭ����дһ���س�����
		outStream.write("\r\n".getBytes());
		// �������ı����͵�ʵ�����ݷ��ͳ���
		outStream.write(textEntity.toString().getBytes());
		// �������ļ����͵�ʵ�����ݷ��ͳ���
		for (FormFile uploadFile : files) {
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile.getInStream() != null) {
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
					outStream.write(buffer, 0, len);
				}
				uploadFile.getInStream().close();
			} else {
				outStream.write(uploadFile.getData(), 0,
						uploadFile.getData().length);
			}
			outStream.write("\r\n".getBytes());
		}
		// ���淢�����ݽ�����־����ʾ�����Ѿ�����
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		if (reader.readLine().indexOf("200") == -1) {// ��ȡweb���������ص����ݣ��ж��������Ƿ�Ϊ200���������200����������ʧ��
			return false;
		}
		outStream.flush();
		outStream.close();
		reader.close();
		socket.close();
		return true;
	}

	/**
	 * �ύ���ݵ�������
	 */
	public static boolean post(String path, Map<String, String> params,
			FormFile file) throws Exception {
		return post(path, params, new FormFile[] { file });
	}
	
	@Override
	public void run() {
//		uploadFile(file);
		httpClientUpload();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				textView.setText("upload Success");
			}
		});
	}

	
	private void httpClientUpload(){
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			MultipartEntity part = new MultipartEntity();
			File parent = Environment.getExternalStorageDirectory();
			File uploadFile = new File(parent,"liuwei.jpg");
			
			FileBody fileBody = new FileBody(uploadFile);
			part.addPart("file",fileBody);
			post.setEntity(part);
			HttpResponse response = client.execute(post);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				System.out.println(EntityUtils.toString(response.getEntity()));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}