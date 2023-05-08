package com.mobileclient.imgCache;

import com.mobileclient.activity.R;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageLoadListener implements OnImageLoadListener {

	private ListView mListView; 
	int imgResourceId;
	
	public ImageLoadListener(ListView listView, int imgResourceId) {
		this.mListView = listView;
		this.imgResourceId = imgResourceId;
	}
	
	@Override  
    public void onImageLoad(Integer t, Bitmap bitmap) { 
        View view = mListView.findViewWithTag("listViewTAG" + t); 
        if(view != null){
            ImageView iv = (ImageView) view.findViewById(imgResourceId);  
            iv.setImageBitmap(bitmap);
        }
    }  
    @Override  
    public void onError(Integer t) {
        View view = mListView.findViewWithTag("listViewTAG" + t);  
        if(view != null){  
            ImageView iv = (ImageView) view.findViewById(imgResourceId);  
            iv.setImageResource(R.drawable.register);
        }  
    }  

}
