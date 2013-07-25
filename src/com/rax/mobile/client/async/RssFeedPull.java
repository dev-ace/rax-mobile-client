package com.rax.mobile.client.async;

import java.io.File;
import java.util.List;

import com.rackspace.mobile.sdk.CloudFiles;


import android.os.AsyncTask;
import android.util.Log;

public class RssFeedPull extends AsyncTask<String, Void, String> {
	
    private final static String USER="mossoths";
    private final static String API_KEY="99b917af206ae042f3291264e0b78a84";
	private List<File>filesToUpload;
	private String container;

	public RssFeedPull(String container, List<File>filesToUpload){
		this.container=container;
		this.filesToUpload=filesToUpload;
	}

	@Override
	protected void onPostExecute(String result){
		Log.d("postExecute:","@@@@@@@@@@@@@@@@@ START:");

		try{

		}
		catch(Throwable e){
			Log.d("onPostExecute()","^^^^^^^^^^Throwable caught message: "+e.getMessage(),e);
		}
		Log.d("postExecute:","@@@@@@@@@@@@@@@@@ END:");
	}


	@Override
	protected String doInBackground(String... params) {		
		Log.d("doInBackground():"," START: params.length="+params.length+" filesToUpload="+filesToUpload);
		String retVal="";
		if(null!=this.filesToUpload){
			Log.d("doInBackground():"," params.length="+params.length+" filesToUpload.size()="+filesToUpload.size());	
			retVal=processFeed();
		}Log.d("doInBackground():"," END: retVal="+retVal);
		return retVal;
	}


	public String processFeed(){
		String retVal="";
		try{
			CloudFiles cloudFiles=new CloudFiles(USER, API_KEY);
			if(null==this.container){
				this.container="fimages";
			}
			retVal+=cloudFiles.uploadFile(this.container, this.filesToUpload);
		}
		catch(Throwable e){
			Log.d("onPostExecute()","^^^^^^^^^^Throwable caught message: "+e.getMessage(),e);
		}
		return retVal;
	}
}
