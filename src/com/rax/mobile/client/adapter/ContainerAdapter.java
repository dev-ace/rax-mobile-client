package com.rax.mobile.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.rax.mobile.client.helper.ImageLoader;


import com.rax.mobile.client.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContainerAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>>data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader;
	
	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		if(null==convertView){
			vi=inflater.inflate(R.layout.container_list_row, null);
			
			TextView fileName=(TextView)vi.findViewById(R.id.filename);
			TextView count=(TextView)vi.findViewById(R.id.count);
			TextView size=(TextView)vi.findViewById(R.id.size);
			ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
			
			Map<String,String> file=this.data.get(position);
			
			fileName.setText(file.get("filename"));
			count.setText(file.get("count"));
			size.setText(file.get("size"));
		    this.imageLoader.DisplayImage(R.id.list_image, thumb_image);
			
		}
		return vi;
	}

}
