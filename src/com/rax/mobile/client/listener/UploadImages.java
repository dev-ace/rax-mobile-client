package com.rax.mobile.client.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.immersion.uhl.Launcher;
import com.rax.mobile.client.async.RssFeedPull;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class UploadImages implements OnClickListener {
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private Context context;
	private Launcher hapticLauncher;
	
	public static final String PUBLIC_ADDRESS_STR="public0_v4";
	public static final String PRIVATE_JSON_IP_ADDRESS="private0_v4";
	public static final String ZONE = "DFW";
	
	public static final String X_STORAGE_TOKEN="X-Storage-Token";
	public static final String X_STORAGE_URL="X-Storage-Url";
	public static final String X_SERVER_MANAGEMENT_URL="X-Server-Management-Url";
	public static final String X_CDN_MANAGEMENT_URL="X-CDN-Management-Url";
	public static final String X_AUTH_TOKEN="X-Auth-Token";

	
	public static final String AUTH_URL="https://identity.api.rackspacecloud.com/v2.0/tokens";
	
	public UploadImages(Context context, boolean[] thumbnailsselection, String[] arrPath){
		super();
		this.context=context; 
		this.thumbnailsselection=thumbnailsselection;
		this.arrPath=arrPath;
	}

	public void setHapticLauncher(Launcher launcher){
		this.hapticLauncher=launcher;
	}
	
	@Override
	public void onClick(View v) {
		final int len = thumbnailsselection.length;
		int cnt = 0;
		String selectImages = "";

		
		List<File>filesToUpload=new ArrayList<File>();
		for (int i =0; i<len; i++)
		{
			if (thumbnailsselection[i]){
				cnt++;
				selectImages = selectImages + arrPath[i] + "|";
				File theFile=new File(arrPath[i]);
				filesToUpload.add(theFile);
			}
		}
		
        RssFeedPull feed=new RssFeedPull("fimages",filesToUpload);
		feed.execute("");
		if (cnt == 0){
			Toast.makeText(this.context,
					"Please select at least one image",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this.context,
					"Uploading " + cnt + " image(s).",
					Toast.LENGTH_LONG).show();
			Log.d("SelectedImages", selectImages);
			System.out.println("~~~~~~~SelectedImages"+ selectImages);
		}
		if(null!=this.hapticLauncher){
			this.hapticLauncher.play(Launcher.BUMP_33);
		}
	}

}
