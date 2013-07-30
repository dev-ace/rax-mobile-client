package com.rax.mobile.client.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rax.mobile.client.R;
import com.rax.mobile.client.helper.ImageLoader;

public class ContainerAdapter extends BaseAdapter {

	private Activity activity;
	private List<Map<String, String>>data;
	private LayoutInflater inflater=null;
	public ImageLoader imageLoader;
	private static boolean debug=true;
	
	public ContainerAdapter(Activity a, List<Map<String, String>> d) {
		String METHOD_NAME="ContainerAdapter:ContainerAdapter() Constructor:";
		this.activity=a;
		this.data=d;
		if(debug){
			for(Map<String, String> aMap:data){
				Log.d(METHOD_NAME,": aMap.get(\"name\")="+aMap.get("name"));
			}
		}
		this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader=new ImageLoader(this.activity.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		return this.data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		TextView fileName=null;
		TextView count=null;
		ImageView thumb_image=null;
		String fileNameStr=null;
		String countStr=null;
		
		//This is the first time we have encountered this row, create it 
		if(null==v){
			v=this.inflater.inflate(R.layout.container_list_row, null);
			
			fileName=(TextView)v.findViewById(R.id.filename);
			count=(TextView)v.findViewById(R.id.count);
			//TextView size=(TextView)vi.findViewById(R.id.size);
			thumb_image=(ImageView)v.findViewById(R.id.list_image);
						
			
			v.setTag(R.id.filename, fileName);
			v.setTag(R.id.count,count);
			v.setTag(R.id.list_image,thumb_image);
		
		}
		//This row has been encountered before, just retrieve it
		else{
			fileName=(TextView)v.getTag(R.id.filename);
			count=(TextView)v.getTag(R.id.count);
			thumb_image=(ImageView)v.getTag(R.id.list_image);
		}
		Map<String,String> aContainer=this.data.get(position);
		fileNameStr=aContainer.get("name");
		countStr=aContainer.get("count");
		
		fileName.setText(fileNameStr);
		count.setText(countStr);		
		this.imageLoader.DisplayImage(R.drawable.folder, thumb_image);
		
		return v;
	}

}
