package com.lw.http01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import com.lw.bean.Person;
import com.lw.bean.SchoolInfo;

public class JsonThread extends Thread {

	private String url;
	private Context context;
	private ListView listView;
	private JsonAdapter jsonAdapter;
	private Handler handler;


	public JsonThread(String url, Context context, ListView listView,
			JsonAdapter jsonAdapter, Handler handler) {
		this.url = url;
		this.context = context;
		this.listView = listView;
		this.jsonAdapter = jsonAdapter;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			URL httpurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpurl
					.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			StringBuffer sb = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			final List<Person> parseJson = parseJson(sb.toString());
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					jsonAdapter.setData(parseJson);
					listView.setAdapter(jsonAdapter);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Person> parseJson(String json) {
		List<Person> persons = new ArrayList<Person>();
		try {
			JSONObject jsonObj = new JSONObject(json);
			int result = jsonObj.getInt("result");

			if (result == 2) {
				JSONArray personData = jsonObj.getJSONArray("personData");

				for (int i = 0; i < personData.length(); i++) {
					Person personObj = new Person();
					List<SchoolInfo> scInfos = new ArrayList<SchoolInfo>();

					JSONObject person = personData.getJSONObject(i);
					personObj.setName(person.getString("name"));
					personObj.setAge(person.getInt("age"));
					personObj.setUrl(person.getString("url"));

					personObj.setSchoolInfo(scInfos);

					JSONArray scs = person.getJSONArray("schoolInfo");
					for (int j = 0; j < scs.length(); j++) {
						SchoolInfo schoolObj = new SchoolInfo();
						JSONObject school = scs.getJSONObject(j);
						schoolObj.setName(school.getString("name"));
						scInfos.add(schoolObj);
					}

					persons.add(personObj);

				}
			} else {
				Toast.makeText(context, "³ö´íÁË", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return persons;
	}

}
