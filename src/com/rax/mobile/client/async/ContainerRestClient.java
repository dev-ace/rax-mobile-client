package com.rax.mobile.client.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rackspace.mobile.exception.CloudFileHttpResponseException;
import com.rackspace.mobile.saxhandler.ContainerSaxHandler.Container;
import com.rackspace.mobile.sdk.CloudFiles;
import com.rax.mobile.client.R;
import com.rax.mobile.client.adapter.ContainerAdapter;

public class ContainerRestClient extends AsyncTask<String, Void, List<Map<String, String>> > {

    public final static String USER_NAME="mossoths";
    public final static String API_KEY="99b917af206ae042f3291264e0b78a84";
    private static final boolean debug=false;

    Activity activity;
    //List<Map<String, String>>containerList;
    
	public ContainerRestClient(Activity activity){
		super();
		this.activity=activity;
	}

	@Override
	protected void onPostExecute(List<Map<String, String>> theList){
		String METHOD_NAME="ContainerRestClient.onPostExecute()";
		if(debug){
			Log.d(METHOD_NAME,": START:");

			for(Map<String, String> aMap:theList){
				Log.d(METHOD_NAME,": aMap.get(\"name\")="+aMap.get("name"));
			}
		}
		try{
			ContainerAdapter adapter=new ContainerAdapter(this.activity, theList);
			
			ListView list=(ListView)this.activity.findViewById(R.id.list);
			list.setAdapter(adapter);
			ProgressBar progBar=(ProgressBar)this.activity.findViewById(R.id.container_progress_id);
			progBar.setVisibility(ProgressBar.INVISIBLE);
		}
		catch(Throwable e){
			Log.e(METHOD_NAME,":Throwable caught message: "+e.getMessage(),e);
		}
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
	}


	@Override
	protected List<Map<String, String>> doInBackground(String... params) {	
		String METHOD_NAME="ContainerRestClient.doInBackground()";
		if(debug){
			Log.d(METHOD_NAME," START: ");
		}
		List<Map<String, String>> retVal=null;
		if(debug){
			Log.d(METHOD_NAME," params.length="+params.length);
		}
		try {
			retVal=getContainersList();
			//this.containerList=retVal;
		} 
		catch (CloudFileHttpResponseException e) {
			Log.e(METHOD_NAME,"CloudFileHttpResponseException: "+e.getMessage()+" "+e);
			e.printStackTrace();
			Toast.makeText(this.activity, "CloudFileHttpResponseException could not contact server error: "+e.getMessage(), Toast.LENGTH_LONG).show();
		
		}
		catch (Throwable e) {
			Log.e(METHOD_NAME,"Throwable: "+e.getMessage()+" "+e);			
			e.printStackTrace();
			Toast.makeText(this.activity, "Throwable could not contact server error: "+e.getMessage(), Toast.LENGTH_LONG).show();
		}
		if(debug){
			Log.d(METHOD_NAME," END: retVal="+retVal);
		}
		return retVal;
	}
	
	private List<Map<String, String>> getContainersList()throws CloudFileHttpResponseException{
		String METHOD_NAME="ContainerRestClient.getContainersList()";
		List<Map<String,String>> retVal=new ArrayList<Map<String,String>>();
		
		CloudFiles cloudFiles=new CloudFiles(USER_NAME, API_KEY);
		List<Container>containersList=cloudFiles.getContainers();
		if(debug){
			Log.d(METHOD_NAME,": START: containersList.size()="+containersList.size());
		}
		for(Container aContainer:containersList){
			Map<String,String>aMap=new HashMap<String, String>();
			String name=aContainer.getName();
			String count=aContainer.getCount().toString();
			String size=aContainer.getBytes().toString();
			if(debug){
				Log.d(METHOD_NAME,": name="+name);
			}
			aMap.put("name", name);
			aMap.put("count", count);
			aMap.put("size", size);
			
			retVal.add(aMap);
		}
		if(debug){
			Log.d(METHOD_NAME,": END: retVal.size()="+retVal.size());
		}
		return retVal;
	}
}

