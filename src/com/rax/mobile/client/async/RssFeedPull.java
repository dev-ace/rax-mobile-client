package com.rax.mobile.client.async;

import java.io.File;
import java.util.List;

import com.rackspace.mobile.sdk.CloudFiles;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class RssFeedPull extends AsyncTask<String, Void, String> {
	
    private final static String USER="mossoths";
    private final static String API_KEY="99b917af206ae042f3291264e0b78a84";
	private List<File>filesToUpload;
	private String container;
	private Context context;
	private static final boolean debug=false;

	public RssFeedPull(Context context, String container, List<File>filesToUpload){
		this.context=context;
		this.container=container;
		this.filesToUpload=filesToUpload;
	}

	@Override
	protected void onPostExecute(String result){
		String METHOD_NAME="RssFeedPull.onPostExecute()";
		if(debug){
			Log.d(METHOD_NAME,": START:");
		}
		try{
			Toast.makeText(this.context, "Successfully uploaded "+ filesToUpload.size()+
					" file(s)", Toast.LENGTH_LONG).show();
		}
		catch(Throwable e){
			e.printStackTrace();
			Log.e(METHOD_NAME,": Throwable caught message: "+e.getMessage(),e);
		}
		if(debug){
		    Log.d(METHOD_NAME,": END:");
		}
	}


	@Override
	protected String doInBackground(String... params) {	
		if(debug){
		Log.d("doInBackground():"," START: params.length="+params.length+" filesToUpload="+filesToUpload);
		}
		String retVal="";
		if(null!=this.filesToUpload){
			if(debug){
				Log.d("doInBackground():"," params.length="+params.length+" filesToUpload.size()="+filesToUpload.size());
			}
			retVal=processFeed();
		}
		if(debug){
			Log.d("doInBackground():"," END: retVal="+retVal);
		}
		return retVal;
	}


	public String processFeed(){
		String METHOD_NAME="RssFeedPull.processFeed()";
		String retVal="";
		try{
			CloudFiles cloudFiles=new CloudFiles(USER, API_KEY);
			if(null==this.container){
				this.container="fimages";
			}
			retVal+=cloudFiles.uploadFile(this.container, this.filesToUpload);
		}
		catch(Throwable e){
			Log.e(METHOD_NAME,":Throwable caught message: "+e.getMessage(),e);
			e.printStackTrace();
		}
		return retVal;
	}
}
