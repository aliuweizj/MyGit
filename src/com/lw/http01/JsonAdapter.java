package com.lw.http01;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.bean.Person;
import com.lw.bean.SchoolInfo;

public class JsonAdapter extends BaseAdapter {

	private List<Person> list;
	private Context context;
	private LayoutInflater inflater;
	private Handler handler = new Handler();
	
	public JsonAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public JsonAdapter(List<Person> list, Context context) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	public void setData(List<Person> list){
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Person person = list.get(position);
		holder.name.setText(person.getName());
		holder.age.setText(person.getAge()+"");

		List<SchoolInfo> scs = person.getSchoolInfo();
		holder.school1.setText(scs.get(0).getName());
		holder.school2.setText(scs.get(1).getName());
		new HttpImage(holder.imageView, person.getUrl(), handler).start();
		return convertView;
	}

	class ViewHolder {
		private TextView name;
		private TextView age;
		private TextView school1;
		private TextView school2;
		private ImageView imageView;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.name);
			age = (TextView) view.findViewById(R.id.age);
			school1 = (TextView) view.findViewById(R.id.school1);
			school2 = (TextView) view.findViewById(R.id.school2);
			imageView = (ImageView) view.findViewById(R.id.imageView);
		}
	}
}
