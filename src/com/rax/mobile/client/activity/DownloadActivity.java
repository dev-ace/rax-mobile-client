package com.rax.mobile.client.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.immersion.uhl.Launcher;
import com.rackspace.mobile.saxhandler.ContainerSaxHandler.Container;
import com.rax.mobile.client.R;
import com.rax.mobile.client.async.ContainerRestClient;

public class DownloadActivity extends Activity {

	private ListView list;
	
	private String USER_NAME="mossoths";
	private String API_KEY="99b917af206ae042f3291264e0b78a84";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_activity);

		ContainerRestClient containerRestClient=new ContainerRestClient(this);
		containerRestClient.execute("doIt");
		//List<Map<String,String>>containerList=containerRestClient.getList();
		
		this.list=(ListView)super.findViewById(R.id.list);
		final ListView lv=this.list;
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				            Map<String, String> theContainerMap=(Map<String,String>)lv.getItemAtPosition(position);
							Toast.makeText(DownloadActivity.this, "selected: "+theContainerMap.get("name"), Toast.LENGTH_LONG).show();

			}
        });
//		try {
//			List<Map<String,String>>containersList=getContainersList();
//			ContainerAdapter adapter=new ContainerAdapter(this,containersList);
//			this.list.setAdapter(adapter);
//	        // Click event for single list row
//	        list.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//								
//
//				}
//			});	
//		} 
//		catch (CloudFileHttpResponseException e) {
//			Toast.makeText(this, "Could not contact server", Toast.LENGTH_LONG).show();
//			e.printStackTrace();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choices, menu);
		return true;
	}
	
//	private List<Map<String, String>> getContainersList()throws CloudFileHttpResponseException{
//		List<Map<String,String>> retVal=new ArrayList<Map<String,String>>();
//		
//		CloudFiles cloudFiles=new CloudFiles(USER_NAME, API_KEY);
//		List<Container>containersList=cloudFiles.getContainers();
//		for(Container aContainer:containersList){
//			Map<String,String>aMap=new HashMap<String, String>();
//			String name=aContainer.getName();
//			String count=aContainer.getCount().toString();
//			String size=aContainer.getBytes().toString();
//			aMap.put("name", name);
//			aMap.put("count", count);
//			aMap.put("size", size);
//			
//			retVal.add(aMap);
//		}		
//		return retVal;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.home_item :{
        	    Intent intent=new Intent(DownloadActivity.this,ChoicesActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(DownloadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
	        }
            case R.id.config_item:{
        	    Intent intent=new Intent(DownloadActivity.this,ConfigActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(DownloadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.download_item:{
        	    Intent intent=new Intent(DownloadActivity.this,DownloadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(DownloadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.upload_item:{
        	    Intent intent=new Intent(DownloadActivity.this,UploadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(DownloadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }            
            default :{
            	return super.onOptionsItemSelected(item);
            }
        
	    }
	}
	
	public ListView getListView(){
		return this.list;
	}
}
