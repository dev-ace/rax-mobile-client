package com.rax.mobile.client.listener;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.rackspace.mobile.exception.CloudFileHttpResponseException;
import com.rackspace.mobile.sdk.ACloudFile;
import com.rackspace.mobile.sdk.CloudFiles;
import com.rax.mobile.client.activity.DownloadFilesActivity;
import com.rax.mobile.client.async.ContainerRestClient;
import com.rax.mobile.client.async.DownloadStorageFile;

public class DownloadFileOnItemClickListener implements OnItemClickListener {

	private static final boolean debug=false;
	private ListView list;
	private Context context;
	private String container;
	
	public DownloadFileOnItemClickListener(Context context, ListView list, String container){
		this.list=list;
		this.context=context;		
		this.container=container;
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long theId) {
		String METHOD_NAME="DownloadFileOnItemClickListener.onItemClick()";
	    if(debug){
	    	Log.d(METHOD_NAME,": START: this.container="+this.container);
	    }
		ACloudFile aCloudFile=(ACloudFile)this.list.getItemAtPosition(position);
	    
        DownloadStorageFile downLoadFile=new DownloadStorageFile(this.context);
        downLoadFile.execute(this.container,aCloudFile.getName());
        
	    if(debug){
	    	Log.d(METHOD_NAME,": END:");
	    }
	}
	
	private void outputFiles(File aFile){
		String METHOD_NAME="DownloadFileOnItemClickListener.outputFiles()";
		File[] pics=aFile.listFiles();


		for(File aPic:pics){
		    	Log.d(METHOD_NAME,": aPic.getAbsolutePath()="+aPic.getAbsolutePath());
		    	if(aPic.isDirectory()){
		    		File[] innerFiles=aPic.listFiles();
		    		Log.d(METHOD_NAME,": $%$%$%$%$%$%$%$Inner folder START:");
		    		for(int j=0;j<innerFiles.length;++j){
		    			File anInnerFile=innerFiles[j];
		    			Log.d(METHOD_NAME,": anInnerFile.getAbsolutePath()="+anInnerFile.getAbsolutePath());
		    		}
		    		Log.d(METHOD_NAME,": $%$%$%$%$%$%$%$Inner folder END:");
		    	}
		    	else{
		    		Log.d(METHOD_NAME,": aFile.getAbsolutePath()="+aFile.getAbsolutePath());
		    	}
		}
		
	}

}
