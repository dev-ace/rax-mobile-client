package com.rax.mobile.client.activity;

import com.immersion.uhl.Launcher;
import com.rax.mobile.client.R;
import com.rax.mobile.client.listener.UploadImages;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

public class UploadActivity extends Activity {

    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;
    
    Launcher haptic;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_activity);
		getThumnails();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.home_item :{
        	    Intent intent=new Intent(UploadActivity.this,ChoicesActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(UploadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
	        }
            case R.id.config_item:{
        	    Intent intent=new Intent(UploadActivity.this,ConfigActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(UploadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.download_item:{
        	    Intent intent=new Intent(UploadActivity.this,DownloadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(UploadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }
            case R.id.upload_item:{
        	    Intent intent=new Intent(UploadActivity.this,UploadActivity.class);
        	    super.startActivity(intent);
				Launcher haptic=new Launcher(UploadActivity.this);
				haptic.play(Launcher.BUMP_33);
        	    return true;
            }            
            default :{
            	return super.onOptionsItemSelected(item);
            }
        
	    }
	}	
	
	//NOTE: This method does not work on an emulator that does not have an emulated external SD Card 
	private void getThumnails(){
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imagecursor = super.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                    getApplicationContext().getContentResolver(), id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, null);
            arrPath[i]= imagecursor.getString(dataColumnIndex);
        }
        GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);
        imagecursor.close();
 
        final Button selectBtn = (Button) findViewById(R.id.selectBtn);
        UploadImages selectButton=new UploadImages(getApplicationContext(), this.thumbnailsselection, this.arrPath);
        selectButton.setHapticLauncher(this.haptic);
        selectBtn.setOnClickListener(selectButton);		
	}
	
	public class ImageAdapter extends BaseAdapter {
	    private LayoutInflater mInflater;

	    public ImageAdapter() {
	        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    public int getCount() {
	        return count;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        if (convertView == null) {
	            holder = new ViewHolder();
	            convertView = mInflater.inflate(
	                    R.layout.gallery_layout, null);
	            holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
	            holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

	            convertView.setTag(holder);
	        }
	        else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	        holder.checkbox.setId(position);
	        holder.imageview.setId(position);	       
	        
	        holder.checkbox.setOnClickListener(new OnClickListener() {

	            public void onClick(View v) {
	                CheckBox cb = (CheckBox) v;
	                int id = cb.getId();
	                if (thumbnailsselection[id]){
	                    cb.setChecked(false);
	                    thumbnailsselection[id] = false;
	                } 
	                else {
	                    cb.setChecked(true);
	                    thumbnailsselection[id] = true;
	                }
	            }
	        });
	        
	        
//	        holder.imageview.setOnClickListener(new OnClickListener() {
//				
//				@Override
//	            public void onClick(View v) {
//	                int id = v.getId();
//	                Intent intent = new Intent();
//	                intent.setAction(Intent.ACTION_VIEW);
//	                intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
//	                startActivity(intent);
//	            }
//				
//			});
	        
	        holder.imageview.setImageBitmap(thumbnails[position]);
	        holder.checkbox.setChecked(thumbnailsselection[position]);
	        holder.id = position;
	        return convertView;
	    }
	}
	
    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
}
