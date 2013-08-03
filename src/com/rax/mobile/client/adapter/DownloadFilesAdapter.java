package com.rax.mobile.client.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rackspace.mobile.sdk.ACloudFile;
import com.rax.mobile.client.R;
import com.rax.mobile.client.helper.ImageLoader;

public class DownloadFilesAdapter extends BaseAdapter {

	private Activity activity;
	private List<ACloudFile>data;
	private LayoutInflater inflater=null;
	public ImageLoader imageLoader;
	private static final boolean debug=false;
	
	
	public DownloadFilesAdapter(Activity activity, List<ACloudFile>data){
		String METHOD_NAME="DownloadFilesAdapter.Constructor()";
		if(debug){
			Log.d(METHOD_NAME,": START:");
		}
		this.activity=activity;
		this.data=data;
		if(debug){
			for(ACloudFile aCloudFile:data){
				Log.d(METHOD_NAME,": aCloudFile="+aCloudFile);
			}
		}
		this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader=new ImageLoader(this.activity.getApplicationContext(),R.drawable.file);	
		
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
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
		TextView size=null;
		ImageView thumb_image=null;
		String fileNameStr=null;
		String sizeStr=null;
		
		//This is the first time we have encountered this row, create it 
		if(null==v){
			v=this.inflater.inflate(R.layout.files_list_row, null);
//			fileName=(TextView)v.findViewById(R.id.container_filename);
//			size=(TextView)v.findViewById(R.id.file_size);
//			thumb_image=(ImageView)v.findViewById(R.id.list_file_image);
			
//			v.setTag(R.id.container_filename, fileName);
//			v.setTag(R.id.file_size,size);
//			v.setTag(R.id.list_file_image,thumb_image);
		}
		//This row has been encountered before, just retrieve it
//		else{
//			fileName=(TextView)v.getTag(R.id.container_filename);
//			size=(TextView)v.getTag(R.id.file_size);
//			thumb_image=(ImageView)v.findViewById(R.id.list_file_image);
//		}
		fileName=(TextView)v.findViewById(R.id.container_filename);
		size=(TextView)v.findViewById(R.id.file_size);
		
		ACloudFile aFile=this.data.get(position);
		fileNameStr=aFile.getName();
		sizeStr=aFile.getBytes();
		
		fileName.setText(fileNameStr);
		size.setText(sizeStr);		
		thumb_image=(ImageView)v.findViewById(R.id.list_file_image);
		
		this.imageLoader.DisplayImage(R.drawable.file, thumb_image);
		return v;
	}

}
