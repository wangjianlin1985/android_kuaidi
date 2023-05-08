package com.mobileclient.imgCache; 

import java.io.BufferedOutputStream;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.lang.ref.SoftReference;  
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;  
import java.util.HashMap;  

import com.mobileclient.app.Declare;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.MD5Util;
 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;  
import android.os.Handler;  
import android.widget.Toast;
  
public class SyncImageLoader {  
  
    private Object lock = new Object();  
  
    private boolean mAllowLoad = true;  
  
    private boolean firstLoad = true;  
  
    private int mStartLoadLimit = 0;  
    
	private int mStopLoadLimit = 0;  
	
	 
  
    final Handler handler = new Handler();  
  
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();  
  
    RunInOtherThread runInOutherThread;  
  
    private static SyncImageLoader instance = new SyncImageLoader();  
    private SyncImageLoader() {  
        super();  
        runInOutherThread = new RunInOtherThread();  
        runInOutherThread.start();  
    }
    public static SyncImageLoader getInstance() {  
        return instance;  
    }  
    
  
    public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {  
        if (startLoadLimit > stopLoadLimit) {  
            return;  
        }  
        mStartLoadLimit = startLoadLimit;  
        mStopLoadLimit = stopLoadLimit;  
    }  
  
    public void restore() {  
        mAllowLoad = true;  
        firstLoad = true;  
    }  
  
    public void lock() {  
        mAllowLoad = false;  
        firstLoad = false;  
    }  
  
    public void unlock() {  
        mAllowLoad = true;  
        synchronized (lock) {  
            lock.notifyAll();  
        }  
    }  
  
    public void loadImage(Integer t, String imageUrl,  
            OnImageLoadListener listener) {  
        final OnImageLoadListener mListener = listener;  
        final String mImageUrl = imageUrl;  
        final Integer mt = t;  
        runInOutherThread.getHandler().post(new Runnable() {
            @Override  
            public void run() { 
                if (!mAllowLoad) {  
                    synchronized (lock) {  
                        try {   
                            lock.wait();  
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }  
                  
                if (mAllowLoad && firstLoad) {  
                    loadImage(mImageUrl, mt, mListener);  
                }  
  
                if (mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit) {  
                    loadImage(mImageUrl, mt, mListener);  
                }  
            }  
  
        });  
    }  
      
    private void loadImage(final String mImageUrl, final Integer mt,  
            final OnImageLoadListener mListener)  {   
        if (imageCache.containsKey(mImageUrl)) { 
            SoftReference<Bitmap> softReference = imageCache.get(mImageUrl);  
            final Bitmap bitmap = softReference.get();  
            if (bitmap != null) {  
                handler.post(new Runnable() {  
                    @Override  
                    public void run() {  
                        if (mAllowLoad) {
                        	mListener.onImageLoad(mt, bitmap);  
                        }  
                    }  
                });  
                return;  
            }  
				 
        }  
        try {   
            final Bitmap bitmap = loadImageFromUrl(mImageUrl);  
            if (bitmap != null) {  
                imageCache.put(mImageUrl, new SoftReference<Bitmap>(bitmap));    
            } else {
            	//Toast.makeText(Declare.context, "下载空", Toast.LENGTH_SHORT).show();
            }
            handler.post(new Runnable() {  
                @Override  
                public void run() {  
                    if (mAllowLoad) { 
                    	mListener.onImageLoad(mt, bitmap);  
                    }  
                }  
            });  
        } catch (Exception e) { 
        	Toast.makeText(Declare.context, mImageUrl + "下载失败，刷新下界面或者检查下图片资源是否存在！", Toast.LENGTH_SHORT).show();
            handler.post(new Runnable() {  
                @Override  
                public void run() {  
                    mListener.onError(mt);  
                }  
            });  
            e.printStackTrace();  
        }  
    }  
  

	public Bitmap loadImageFromUrl(String url) throws IOException { 
		/*缓存文件夹：如果有内存卡就放入内存卡目录，如果没有内存卡就放入系统安装软件包缓存*/
		String dirPath =  HttpUtil.Cach_Dir;
		boolean userSDCache = true; 
        if(userSDCache && Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED)) { 
        	dirPath = HttpUtil.FILE_PATH  + "/cache_photo/";
        }
        /*通过图片url获取图片,如果文件缓存有的话读取文件到内存,如果文件不存在,从网络获取*/
        File directory = new File(dirPath) ; 
        final File f = new File(dirPath +  MD5Util.getMD5String(url)); 
        //如果文件夹不存在则创建    
	    if  (!directory.exists() && !directory.isDirectory()) directory.mkdirs();   
	    if (!f.exists()) {  
	            System.gc();
	            URL myFileUrl = null; 
	            myFileUrl = new URL(url); 
	            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
	            conn.setDoInput(true);
	            conn.connect();
	            InputStream is = conn.getInputStream();
	            Bitmap bitmap = BitmapFactory.decodeStream(is); 
	            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));    
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
	            bos.flush();
	            bos.close();   	
	            return bitmap;  
	     }  
	         
	     System.gc();
	     Bitmap bitmap=BitmapFactory.decodeFile(f.getAbsolutePath());
	     return bitmap;
        }  
		 
	}  
   