package com.rax.mobile.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.rax.mobile.client.R;
import com.rax.mobile.client.async.DownloadFilesRestClient;
import com.rax.mobile.client.listener.DownloadFileOnItemClickListener;

public class DownloadFilesActivity extends Activity{
	
	private String container;
	private ListView listOfContainerFiles;
	private static final boolean debug=false;
	
	public DownloadFilesActivity(){
		super();
	}
	
	public DownloadFilesActivity(String container){
		super();
	    this.container=container;	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String METHOD_NAME="DownloadFilesActivity.onCreate()";
		if(debug){
			Log.d(METHOD_NAME,": START:");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_files_activity);
		Intent intent=super.getIntent();
		Bundle bundle=intent.getExtras();
		this.container=bundle.getString("container");

		DownloadFilesRestClient downloadFiles=new DownloadFilesRestClient(this);
		//this will populate the list of files for a given container
		downloadFiles.execute(this.container);
		
		this.listOfContainerFiles=(ListView)super.findViewById(R.id.files_list);
		DownloadFileOnItemClickListener listListener=new DownloadFileOnItemClickListener(super.getApplicationContext(), 
				this.listOfContainerFiles, this.container);
		this.listOfContainerFiles.setOnItemClickListener(listListener);
		
//		final ListView lv=this.listOfContainerFiles;
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			    ACloudFile aCloudFile=(ACloudFile)lv.getItemAtPosition(position);
//			    Toast.makeText(DownloadFilesActivity.this, "Selected file: "+aCloudFile.getName(), 
//			    		       Toast.LENGTH_LONG).show();
//			}
//		});
		if(debug){
			Log.d(METHOD_NAME,": END:");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choices, menu);
		return true;
	}

}
