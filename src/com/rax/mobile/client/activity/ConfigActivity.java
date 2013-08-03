package com.rax.mobile.client.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.immersion.uhl.Launcher;
import com.rax.mobile.client.R;

public class ConfigActivity extends Activity {
	
	private static final boolean debug=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_activity);
		creatUIListeners();
		getConfiguration();

//		Spinner spinner=(Spinner)super.findViewById(R.id.downloadFolderSpinner);
//		File externalStorageFolder=Environment.getExternalStorageDirectory();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choices, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.home_item :{
        	    Intent intent=new Intent(ConfigActivity.this,ChoicesActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ConfigActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
	        }
            case R.id.config_item:{
        	    Intent intent=new Intent(ConfigActivity.this,ConfigActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ConfigActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.download_item: {
        	    Intent intent=new Intent(ConfigActivity.this,DownloadActivity.class);
        	    super.startActivity(intent);  
				Launcher haptic=new Launcher(ConfigActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.upload_item:{
        	    Intent intent=new Intent(ConfigActivity.this,UploadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ConfigActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            default :{
            	return super.onOptionsItemSelected(item);
            }
        
	    }
	}
	
	private void getConfiguration(){
		String METHOD_NAME="ConfigActivity.getConfiguration()";
		if(debug){
			Log.d(METHOD_NAME,": ENTER:");
		}
		File dir=super.getFilesDir();
		File props=new File(dir, "raxfiles.properties");
		boolean fileExists=true;
		if(!props.exists()){
			fileExists=false;
			try {
				FileOutputStream outty=new FileOutputStream(props);
				outty.write("firstline=1\n".getBytes());
				outty.flush();
				outty.close();
				
			} 
			catch (FileNotFoundException e) {
				Log.e(METHOD_NAME,": FileNotFoundException: ",e);
				e.printStackTrace();
			}
			catch (IOException e) {
				Log.e(METHOD_NAME,": IOException: ",e);
				e.printStackTrace();
			}
		}
		//The properties file exists, we must get values from it 

		Properties properties=new Properties();
		try {
			FileInputStream inny=new FileInputStream(props);
			properties.load(inny);
			String username=properties.getProperty("username","mossoths");

			EditText usernameEditText=(EditText)super.findViewById(R.id.usernameeditext);
			if(debug){
				Log.d(METHOD_NAME,": username="+username);
			}
			usernameEditText.setText(username);

			String apiKey=properties.getProperty("apikey","99b917af206ae042f3291264e0b78a84");
			EditText apiKeyEditText=(EditText)super.findViewById(R.id.apikeyedittext);
			if(debug){
				Log.d(METHOD_NAME,": apiKey="+apiKey);
			}
			apiKeyEditText.setText(apiKey);

			String container=properties.getProperty("container","fimages");
			EditText containerEditText=(EditText)super.findViewById(R.id.containeredittext);
			if(debug){
				Log.d(METHOD_NAME,": container="+container);
			}
			containerEditText.setText(container);
			if(!fileExists){
				if(debug){
					Log.d(METHOD_NAME,": raxfiles.properties does not exist writing default values to it");
				}
				properties.setProperty("username", username);
				properties.setProperty("apikey", apiKey);
				properties.setProperty("container", container);
			}
			inny.close();
		} 
		catch (FileNotFoundException e) {
			Log.e(METHOD_NAME,": FileNotFoundException: ",e);
			e.printStackTrace();
		} 
		catch (IOException e) {
			Log.e(METHOD_NAME,": IOException: ",e);
			e.printStackTrace();
		}

		if(debug){
			Log.d(METHOD_NAME,": EXIT:");
		}
	}
	
	private void creatUIListeners(){
		Button saveConfigButton=(Button)super.findViewById(R.id.saveconfigbtn);
		
		saveConfigButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Properties properties=new Properties();

				String METHOD_NAME="ConfigActivity.creatUIListeners().onClick()";
				

				EditText usernameEditText=(EditText)ConfigActivity.super.findViewById(R.id.usernameeditext);
				String username=usernameEditText.getText().toString();

				if(null==username||username.isEmpty()){
					Toast.makeText(ConfigActivity.this.getApplicationContext(),
							"User Name must not be empty",
							Toast.LENGTH_LONG).show();
				}
				else{
					EditText apiKeyEditText=(EditText)ConfigActivity.super.findViewById(R.id.apikeyedittext);
					String apiKey=apiKeyEditText.getText().toString();
					if(null==apiKey||apiKey.isEmpty()){
						Toast.makeText(ConfigActivity.this.getApplicationContext(),
								"Api Key must not be empty",
								Toast.LENGTH_LONG).show();	
					}
					else{
						EditText containerEditText=(EditText)ConfigActivity.super.findViewById(R.id.containeredittext);
						String container=containerEditText.getText().toString();
						if(null==container||container.isEmpty()){
							Toast.makeText(ConfigActivity.this.getApplicationContext(),
									"Container must not be empty",
									Toast.LENGTH_LONG).show();	
						}
						else{
							File dir=ConfigActivity.super.getFilesDir();
						    File props=new File(dir, "raxfiles.properties");
						    
						    try {
						    	FileInputStream inny=new FileInputStream(props);
								properties.load(inny);
								properties.setProperty("username", username);
								properties.setProperty("apikey", apiKey);
								properties.setProperty("container", container);
								inny.close();
								
								Toast.makeText(ConfigActivity.this.getApplicationContext(),
										"Configuration Saved",
										Toast.LENGTH_LONG).show();
							} 
					    	catch (FileNotFoundException e) {
								Log.e(METHOD_NAME,": FileNotFoundException: ",e);
								e.printStackTrace();
							} 
					    	catch (IOException e) {
					    		Log.e(METHOD_NAME,": IOException: ",e);
								e.printStackTrace();
							}
					    	catch (Throwable e) {
					    		Log.e(METHOD_NAME,": Throwable: ",e);
								e.printStackTrace();
							}	
						}
					}

				}
			}
		});
	}
}
