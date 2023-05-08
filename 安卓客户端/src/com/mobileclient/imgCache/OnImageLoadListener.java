package com.mobileclient.imgCache;

import android.graphics.Bitmap;

public interface OnImageLoadListener {
	  public void onImageLoad(Integer t, Bitmap bitmap);
      public void onError(Integer t);  
}
