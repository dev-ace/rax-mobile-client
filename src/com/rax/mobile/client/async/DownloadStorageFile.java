package com.rax.mobile.client.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rackspace.mobile.exception.CloudFileHttpResponseException;
import com.rackspace.mobile.sdk.CloudFiles;

public class DownloadStorageFile extends AsyncTask<String, Void, String> {
	
    public final static String USER_NAME=ContainerRestClient.USER_NAME;
    public final static String API_KEY=ContainerRestClient.API_KEY;
    
    private Context context;
    private static final boolean debug=false;
    
    public DownloadStorageFile(Context context){    	
    	super();
    	this.context=context;
    }
    
	@Override
	protected void onPostExecute(String retVal){
		String METHOD_NAME="DownloadStorageFile.onPostExecute()";
		if(debug){
			Log.d(METHOD_NAME,": START:");
		}
    	Toast.makeText(this.context, "Successfully downloaded file: "+retVal, 
    			Toast.LENGTH_LONG).show();
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
	}
    
	@Override
	protected String doInBackground(String... params) {		
		String METHOD_NAME="DownloadStorageFile.doInBackground()";
		String retVal=null;
		if(debug){
			Log.d(METHOD_NAME,":START:");
		}
		if(null!=params){
			if(debug){
				Log.d(METHOD_NAME,": params.length="+params.length);
			}
			if(params.length==2){
			    CloudFiles cloudFiles = new CloudFiles(ContainerRestClient.USER_NAME, ContainerRestClient.API_KEY);
			    String container=params[0];
			    retVal=params[1];
			    try{
			    	cloudFiles.downloadStorageFileFromContainer(container, retVal);
			    }
			    catch(CloudFileHttpResponseException e){
			    	Toast.makeText(this.context, "Error downloading file: "+retVal+" "+e.getMessage(), Toast.LENGTH_LONG).show();
			    	Log.e(METHOD_NAME,"CloudFileHttpResponseException error: ",e);
			    }
			    catch(Throwable e){
			    	Toast.makeText(this.context, "Throwable error downloading file: "+retVal+" "+e.getMessage(), Toast.LENGTH_LONG).show();
			    	Log.e(METHOD_NAME,"Throwable error: ",e);
			    }
			}
		}
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
		return retVal;
	}

}
