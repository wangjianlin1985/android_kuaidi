package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.TakeOrder;
import com.mobileclient.service.TakeOrderService;
import com.mobileclient.domain.ExpressTake;
import com.mobileclient.service.ExpressTakeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class TakeOrderDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明订单id控件
	private TextView TV_orderId;
	// 声明代拿的快递控件
	private TextView TV_expressTakeObj;
	// 声明接任务人控件
	private TextView TV_userObj;
	// 声明接单时间控件
	private TextView TV_takeTime;
	// 声明订单状态控件
	private TextView TV_orderStateObj;
	// 声明实时动态控件
	private TextView TV_ssdt;
	// 声明用户评价控件
	private TextView TV_evaluate;
	/* 要保存的代拿订单信息 */
	TakeOrder takeOrder = new TakeOrder(); 
	/* 代拿订单管理业务逻辑层 */
	private TakeOrderService takeOrderService = new TakeOrderService();
	private ExpressTakeService expressTakeService = new ExpressTakeService();
	private UserInfoService userInfoService = new UserInfoService();
	private OrderStateService orderStateService = new OrderStateService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.takeorder_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看代拿订单详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_expressTakeObj = (TextView) findViewById(R.id.TV_expressTakeObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_takeTime = (TextView) findViewById(R.id.TV_takeTime);
		TV_orderStateObj = (TextView) findViewById(R.id.TV_orderStateObj);
		TV_ssdt = (TextView) findViewById(R.id.TV_ssdt);
		TV_evaluate = (TextView) findViewById(R.id.TV_evaluate);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TakeOrderDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    takeOrder = takeOrderService.GetTakeOrder(orderId); 
		this.TV_orderId.setText(takeOrder.getOrderId() + "");
		ExpressTake expressTakeObj = expressTakeService.GetExpressTake(takeOrder.getExpressTakeObj());
		this.TV_expressTakeObj.setText(expressTakeObj.getTaskTitle());
		UserInfo userObj = userInfoService.GetUserInfo(takeOrder.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_takeTime.setText(takeOrder.getTakeTime());
		OrderState orderStateObj = orderStateService.GetOrderState(takeOrder.getOrderStateObj());
		this.TV_orderStateObj.setText(orderStateObj.getOrderStateName());
		this.TV_ssdt.setText(takeOrder.getSsdt());
		this.TV_evaluate.setText(takeOrder.getEvaluate());
	} 
}
