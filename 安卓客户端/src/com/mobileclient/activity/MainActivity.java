 
package com.mobileclient.activity;
 

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.mobileclient.util.ActivityUtils;
 

public class MainActivity extends MyTabActivity {

	private static final String FIRST_TAB = "first";
	private static final String SECOND_TAB = "second";
	private static final String THIRD_TAB = "third";
	private static final String FOURTH_TAB = "fourth";

	private TabHost tabHost;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//去除title   
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
				
		setContentView(R.layout.main);

		tabHost = this.getTabHost();

		/*第一tab页 */
		TabSpec firstSpec = tabHost.newTabSpec(FIRST_TAB).setIndicator(FIRST_TAB)
				.setContent(new Intent(this, UserInfoListActivity.class));
		Button firstBtn = (Button)findViewById(R.id.firstBtn);
		firstBtn.setText("用户");
		/*第二tab页*/
		TabSpec secondSpec = tabHost.newTabSpec(SECOND_TAB).setIndicator(SECOND_TAB)
				.setContent(new Intent(this,ExpressTakeListActivity.class));
		Button secondBtn = (Button)findViewById(R.id.secondBtn);
		secondBtn.setText("快递代拿");
		/*第三tab页*/
		TabSpec thirdSpec = tabHost.newTabSpec(THIRD_TAB).setIndicator(THIRD_TAB)
				.setContent(new Intent(this,CompanyListActivity.class));
		Button thirdBtn = (Button)findViewById(R.id.thirdBtn);
		thirdBtn.setText("物流公司");
		/*第四tab页*/
		TabSpec fourthSpec = tabHost.newTabSpec(FOURTH_TAB).setIndicator(FOURTH_TAB)
				.setContent(new Intent(this, MoreActivity.class));
		Button fourthBtn = (Button)findViewById(R.id.fourthBtn);
		fourthBtn.setText("更多");
		tabHost.addTab(firstSpec);
		tabHost.addTab(secondSpec);
		tabHost.addTab(thirdSpec);
		tabHost.addTab(fourthSpec);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.rg_main_btns);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.firstBtn:
					tabHost.setCurrentTabByTag(FIRST_TAB);
					break;

				case R.id.secondBtn:
					tabHost.setCurrentTabByTag(SECOND_TAB);
					break;

				case R.id.thirdBtn:
					tabHost.setCurrentTabByTag(THIRD_TAB);
					break;

				case R.id.fourthBtn:
					tabHost.setCurrentTabByTag(FOURTH_TAB);
					break;

				default:
					break;
				}

			}
		});

	}

 
    
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getAction() == KeyEvent.ACTION_DOWN
    			&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
    		ActivityUtils.getInstance().ConfirmExit(this);
            
    	}
    	
    	return super.dispatchKeyEvent(event);
    };
    
 

}
