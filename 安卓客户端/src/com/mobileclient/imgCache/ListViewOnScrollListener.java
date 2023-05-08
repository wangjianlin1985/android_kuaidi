package com.mobileclient.imgCache;

import android.widget.AbsListView;
import android.widget.ListView;

public class ListViewOnScrollListener implements  AbsListView.OnScrollListener {
	
	private SyncImageLoader syncImageLoader;
	private ListView listView;
	int listViewCount;
	
	public ListViewOnScrollListener(SyncImageLoader syncImageLoader,ListView listView,int listViewCount) {
		this.syncImageLoader = syncImageLoader;
		this.listView = listView;
		this.listViewCount = listViewCount;
	}
	
	public void loadImage(){  
        int start = listView.getFirstVisiblePosition();  
        int end =listView.getLastVisiblePosition();  
        if(end >= listViewCount){  
            end = listViewCount -1;  
        }  
        syncImageLoader.setLoadLimit(start, end);  
        syncImageLoader.unlock();  
    }  
	
	
	//如果需要停止下拉滚动后才加载图片,修改下面的代码就行,我使用的是滚动时也同时加载图片
    @Override  
    public void onScrollStateChanged(AbsListView view, int scrollState) {  
        switch (scrollState) {  
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:  
                //syncImageLoader.lock();  
            	loadImage(); 
                break;  
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                loadImage();   
                break;  
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
                //syncImageLoader.lock();
            	loadImage(); 
                break;  
  
            default:  
                break;  
        }  
          
    }  
      
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem,  
            int visibleItemCount, int totalItemCount) {  
        // TODO Auto-generated method stub  
          
    }  
}
