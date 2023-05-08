package com.mobileclient.imgCache;
 

import android.os.Handler;  
import android.os.Looper;  
import android.os.Message;  
  
  
public class RunInOtherThread {  
    private static final String LOG_TAG = "RunInOtherThread";  
      
    private LooperThread localThread = new LooperThread();  
      
    private boolean isRunning = true;  
  
    public Handler getHandler(){  
        return localThread.getHandler();  
    }  
      
    private class LooperThread extends Thread {  
        private Handler mHandler;  
  
        public void run() {  
            Looper.prepare();  
            mHandler = new Handler() {  
                public void handleMessage(Message msg) {  
                    onReceiveMessage(msg.what);  
                }  
            };  
            Looper.loop();  
        }  
          
        Handler getHandler(){  
            return mHandler;  
        }  
     
    }  
      
    public void start(){  
        localThread.start();  
    }  
      
    public void quit(){  
        localThread.getHandler().getLooper().quit();  
    }  
      
    public void sendMessage(int what){  
        getHandler().sendEmptyMessage(what);  
    }  
      
    public Thread getThread(){  
        return localThread;  
    }  
      
    public void onReceiveMessage(int what){};  
       
}  