package com.rax.mobile.client.async;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rackspace.mobile.exception.CloudFileHttpResponseException;
import com.rackspace.mobile.sdk.ACloudFile;
import com.rackspace.mobile.sdk.CloudFiles;
import com.rax.mobile.client.R;
import com.rax.mobile.client.adapter.DownloadFilesAdapter;

public class DownloadFilesRestClient extends AsyncTask<String, Void, List<ACloudFile>> {
	
	private static final boolean debug=false;
	
    private final static String USER_NAME="mossoths";
    private final static String API_KEY="99b917af206ae042f3291264e0b78a84";

    Activity activity;
    //List<Map<String, String>>containerList;
    
	public DownloadFilesRestClient(Activity activity){
		super();
		this.activity=activity;
	}
	
	@Override
	protected List<ACloudFile> doInBackground(String... container) {	
		String METHOD_NAME="DownlaodFilesRestClient.doInBackground()";
		if(debug){
		    Log.d(METHOD_NAME,": START: container="+container);
//		    Log.d(METHOD_NAME,": container.length="+container.length);
//		    Log.d(METHOD_NAME,": container[0]="+container[0]);
		}
		List<ACloudFile> retVal=null;
		if(null!=container && container.length>0 && container[0]!=null && !container[0].isEmpty()){
			if(debug){
			    Log.d(METHOD_NAME,": container[0]="+container[0]);
			    Log.d(METHOD_NAME,": container.length="+container.length);
			}
			try{
				retVal=this.getFilesListFromContainer(container[0]);
			}
			catch(CloudFileHttpResponseException e){
				e.printStackTrace();
				Toast.makeText(this.activity, METHOD_NAME+": CloudFileHttpResponse error: "+e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		if(null==retVal){
			retVal=new ArrayList<ACloudFile>();
		}
		if(debug){		
			Log.d(METHOD_NAME,": END: retVal.size()="+retVal.size());
		}
		return retVal;		
	}
	
	@Override
	protected void onPostExecute(List<ACloudFile> filesList){
		String METHOD_NAME="ContainerRestClient.onPostExecute()";
		if(debug){
			Log.d(METHOD_NAME,": START: filesList");
		}
		if(null!=filesList){
			if(debug){
				Log.d(METHOD_NAME,": filesList.size()="+filesList.size());
			}
			ProgressBar progress=(ProgressBar)this.activity.findViewById(R.id.files_progress_id);
			progress.setVisibility(ProgressBar.INVISIBLE);
			DownloadFilesAdapter adapter=new DownloadFilesAdapter(this.activity, filesList);
			ListView theList=(ListView)this.activity.findViewById(R.id.files_list);
			theList.setAdapter(adapter);
		}
		
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
		
	}
	
	private List<ACloudFile> getFilesListFromContainer(String container)throws CloudFileHttpResponseException{
		String METHOD_NAME="DownlaodFilesRestClient.getFilesListFromContainer()";
		if(debug){
			Log.d(METHOD_NAME,": START: ");
		}
		List<ACloudFile> retVal=new ArrayList<ACloudFile>();
		
		if(null!=container && !container.isEmpty()){
			CloudFiles cloudFiles=new CloudFiles(USER_NAME, API_KEY);
			retVal=cloudFiles.getStorageFilesInContainer(container);
		}
		if(debug){
			Log.d(METHOD_NAME,": END: retVal.size()="+retVal.size());
		}
		return retVal;
		
	}
}
