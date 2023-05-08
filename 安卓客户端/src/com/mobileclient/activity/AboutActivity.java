package com.mobileclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity{
	private TextView title;
	private ImageView search;
	private ImageView back_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去除title   
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
					
		setContentView(R.layout.about);
		title = (TextView)findViewById(R.id.title);
		search = (ImageView)findViewById(R.id.search);
		search.setVisibility(View.GONE);
		title.setText("关于");
		back_btn = (ImageView)this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) { 
				finish();
			}
			
		});	
	}

}
