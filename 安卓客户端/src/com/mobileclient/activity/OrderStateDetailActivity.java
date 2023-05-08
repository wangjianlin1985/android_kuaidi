package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class OrderStateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明订单状态id控件
	private TextView TV_orderStateId;
	// 声明订单状态名称控件
	private TextView TV_orderStateName;
	/* 要保存的订单状态信息 */
	OrderState orderState = new OrderState(); 
	/* 订单状态管理业务逻辑层 */
	private OrderStateService orderStateService = new OrderStateService();
	private int orderStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderstate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单状态详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderStateId = (TextView) findViewById(R.id.TV_orderStateId);
		TV_orderStateName = (TextView) findViewById(R.id.TV_orderStateName);
		Bundle extras = this.getIntent().getExtras();
		orderStateId = extras.getInt("orderStateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderStateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderState = orderStateService.GetOrderState(orderStateId); 
		this.TV_orderStateId.setText(orderState.getOrderStateId() + "");
		this.TV_orderStateName.setText(orderState.getOrderStateName());
	} 
}
