package com.rax.mobile.client.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;


public class ImageLoader {
    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, Drawable> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, Drawable>());
    ExecutorService executorService; 
    Context context;
    final int stub_id;
    
    public ImageLoader(Context context, int drawableResource){
        fileCache=new FileCache(context);
        this.context=context;
        executorService=Executors.newFixedThreadPool(5);
        this.stub_id=drawableResource;
    }
 
    
    
    public void DisplayImage(int img, ImageView imageView)
    {
    	imageView.setImageResource(stub_id);
//        imageViews.put(imageView, this.context.getResources().getDrawable(img));
//        Bitmap bitmap=memoryCache.get(imageView.toString());
        //Bitmap bitmap=BitmapFactory.decodeResource(this.context.getResources(),img);
//        if(bitmap!=null){
//            imageView.setImageBitmap(bitmap);
//        }
//        else{
//        	queuePhoto(this.context.getResources(), imageView, img);
//        	imageView.setImageResource(stub_id);
//        }
    }
 
    private void queuePhoto(Resources res, ImageView imageView, int img)
    {
        PhotoToLoad p=new PhotoToLoad(res, imageView, img);
        executorService.submit(new PhotosLoader(p));
    }
 
    private Bitmap getBitmap(Resources res, int img)
    {
    	try{
    		return BitmapFactory.decodeResource(this.context.getResources(),img);
    	} 
    	catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
    	}
    	//        File f=fileCache.getFile(url);
    	//       
    	//        //from SD cache
    	//        Bitmap b = decodeFile(f);
    	//        if(b!=null)
    	//            return b;
    	// 
    	//        //from web
    	//        try {
    	//            Bitmap bitmap=null;
    	//            URL imageUrl = new URL(url);
    	//            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
    	//            conn.setConnectTimeout(30000);
    	//            conn.setReadTimeout(30000);
    	//            conn.setInstanceFollowRedirects(true);
    	//            InputStream is=conn.getInputStream();
    	//            OutputStream os = new FileOutputStream(f);
    	//            Utils.CopyStream(is, os);
    	//            os.close();
    	//            bitmap = decodeFile(f);
    	//            return bitmap;
    	//        } catch (Exception ex){
    	//           ex.printStackTrace();
    	//           return null;
    	//        }
    }
 
    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
 
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
 
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
 
    //Task for the queue
    private class PhotoToLoad
    {
        public Resources res;
        public ImageView imageView;
        public int img;
        public PhotoToLoad(Resources r, ImageView i, int im){
            res=r;
            imageView=i;
            img=im;
        }
    }
 
    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }
 
        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            
            
            Bitmap bmp=getBitmap(photoToLoad.res,photoToLoad.img);
            memoryCache.put(photoToLoad.res.toString(), bmp);
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
            Activity a=(Activity)photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }
 
    boolean imageViewReused(PhotoToLoad photoToLoad){
//        String tag=imageViews.get(photoToLoad.imageView);
//        if(tag==null || !tag.equals(photoToLoad.url))
//            return true;
//        return false;
    	return true;
    }
 
    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }
 
    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
 
}
