package com.rax.mobile.client.activity;


import com.immersion.uhl.Launcher;

import com.rax.mobile.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChoicesActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choices_activity);
		setupClickListeners();
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
    	        Intent intent=new Intent(ChoicesActivity.this,ChoicesActivity.class);
    	        super.startActivity(intent);
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
    	        return true;
            }	    
            case R.id.config_item:{
            	Log.d("ChoicesActivity.","onOptionsItemSelected(): going to the ConfigActivity!!!!");
        	    Intent intent=new Intent(ChoicesActivity.this,ConfigActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.download_item: {
        	    Intent intent=new Intent(ChoicesActivity.this,DownloadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.upload_item:{
        	    Intent intent=new Intent(ChoicesActivity.this,UploadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            default :{
            	return super.onOptionsItemSelected(item);
            }
        
	    }
	}
	
	private void setupClickListeners(){
		
		View.OnClickListener homeTextListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
				Intent intent=new Intent(ChoicesActivity.this,ChoicesActivity.class);
				ChoicesActivity.super.startActivity(intent);
			}
		};
		
		TextView homeText=(TextView)super.findViewById(R.id.choices_home_text);
		homeText.setOnClickListener(homeTextListener);
		ImageButton homeImageBtn=(ImageButton)super.findViewById(R.id.home_image_btn);		
		homeImageBtn.setOnClickListener(homeTextListener);

		View.OnClickListener cofigurationTextListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
				Intent intent=new Intent(ChoicesActivity.this,ConfigActivity.class);
				ChoicesActivity.super.startActivity(intent);
			}
		};
		
		TextView configText=(TextView)super.findViewById(R.id.choices_config_text);
		configText.setOnClickListener(cofigurationTextListener);
		ImageButton configImageBtn=(ImageButton)super.findViewById(R.id.config_image_btn);		
		configImageBtn.setOnClickListener(cofigurationTextListener);
		
		View.OnClickListener downloadTextListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
				Intent intent=new Intent(ChoicesActivity.this,DownloadActivity.class);
				ChoicesActivity.super.startActivity(intent);
			}
		};
		
		TextView downloadText=(TextView)super.findViewById(R.id.choices_download_text);
		downloadText.setOnClickListener(downloadTextListener);
		ImageButton downloadImageBtn=(ImageButton)super.findViewById(R.id.download_img_btn);		
		downloadImageBtn.setOnClickListener(downloadTextListener);
		
		View.OnClickListener uploadTextListener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Launcher haptic=new Launcher(ChoicesActivity.this);
				haptic.play(Launcher.BUMP_33);
				Intent intent=new Intent(ChoicesActivity.this,UploadActivity.class);
				ChoicesActivity.super.startActivity(intent);
			}
		};		
		
		TextView uploadText=(TextView)super.findViewById(R.id.choices_upload_text);
		uploadText.setOnClickListener(uploadTextListener);
		ImageButton uploadImageBtn=(ImageButton)super.findViewById(R.id.upload_img_btn);		
		uploadImageBtn.setOnClickListener(uploadTextListener);
		
	}	
	
}
