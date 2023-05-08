package com.mobileclient.activity;

 
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageSimpleAdapter;

 
public class photoListActivity extends Activity {
 
	SimpleAdapter adapter;
	ListView lv;
	EditText et;
	String question;
	
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.photolist); 
		//setTitle("当前位置-图片选择"); 
		intent = this.getIntent();
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		adapter = new ImageSimpleAdapter(this, getDatas(),
				R.layout.message_list, new String[] { "icon", "title",
						"shortContent" }, new int[] { R.id.ml_icon,
						R.id.ml_title, R.id.ml_short_content });
		lv.setAdapter(adapter);
		// lv.setOnItemClickListener();

		lv.setOnItemClickListener(myListener1);

		 

	}

	private OnItemClickListener myListener1 = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// 在本例中arg2=arg3
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			setTitle((String) item.get("title"));
			
			/*
			Intent intent = new Intent();
			intent.setClass(photoListActivity.this, ViewFlipperActivity.class);
			startActivity(intent);
			*/
			Bundle bundle = intent.getExtras();
			String fileName = (String)item.get("title");
			intent.putExtra("fileName", fileName);
			try {
			//bundle.putString("filename",fileName); 
			photoListActivity.this.setResult(Activity.RESULT_OK,intent);
			photoListActivity.this.finish();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}

	};
 

	public static int computeSampleSize(BitmapFactory.Options options,  
	        int minSideLength, int maxNumOfPixels) {  
	    int initialSize = computeInitialSampleSize(options, minSideLength,maxNumOfPixels);  
	  
	    int roundedSize;  
	    if (initialSize <= 8 ) {  
	        roundedSize = 1;  
	        while (roundedSize < initialSize) {  
	            roundedSize <<= 1;  
	        }  
	    } else {  
	        roundedSize = (initialSize + 7) / 8 * 8;  
	    }  
	  
	    return roundedSize;  
	}  
	
	private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {  
	    double w = options.outWidth;  
	    double h = options.outHeight;  
	  
	    int lowerBound = (maxNumOfPixels == -1) ? 1 :  
	            (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));  
	    int upperBound = (minSideLength == -1) ? 128 :  
	            (int) Math.min(Math.floor(w / minSideLength),  
	            Math.floor(h / minSideLength));  
	  
	    if (upperBound < lowerBound) {  
	        // return the larger one when there is no overlapping zone.  
	        return lowerBound;  
	    }  
	  
	    if ((maxNumOfPixels == -1) &&  
	            (minSideLength == -1)) {  
	        return 1;  
	    } else if (minSideLength == -1) {  
	        return lowerBound;  
	    } else {  
	        return upperBound;  
	    }  
	}  
	
	
	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File home = new File(HttpUtil.FILE_PATH + "/"); 
		File[] files = home.listFiles();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			File fileChild = files[i];
			String fileName = fileChild.getName();
			if (fileName.lastIndexOf(".") != -1) {
				String lastType = fileName.substring(fileName.lastIndexOf("."));
				if (".gif".equals(lastType) || ".jpg".equals(lastType) || ".bmp".equals(lastType)
						|| ".png".equals(lastType)) {
					String imagePath = HttpUtil.FILE_PATH + "/" + fileName;
					//map.put("icon", BitmapFactory.decodeFile(imagePath));
					
					BitmapFactory.Options opts = new BitmapFactory.Options();  
					opts.inJustDecodeBounds = true;  
					BitmapFactory.decodeFile(imagePath, opts);  
					  
					opts.inSampleSize = computeSampleSize(opts, -1, 128*128);  
					opts.inJustDecodeBounds = false;  
					try {  
						map.put("icon", BitmapFactory.decodeFile(imagePath, opts));
					   
					    } catch (OutOfMemoryError err) {  
					}  
					
					map.put("title", fileName);
					map.put("shortContent", "");
					list.add(map);
				}
			}

		}
		return list;
	}
 

 
}