package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.TakeOrder;
import com.mobileclient.service.TakeOrderService;
import com.mobileclient.domain.ExpressTake;
import com.mobileclient.service.ExpressTakeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class TakeOrderAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明代拿的快递下拉框
	private Spinner spinner_expressTakeObj;
	private ArrayAdapter<String> expressTakeObj_adapter;
	private static  String[] expressTakeObj_ShowText  = null;
	private List<ExpressTake> expressTakeList = null;
	/*代拿的快递管理业务逻辑层*/
	private ExpressTakeService expressTakeService = new ExpressTakeService();
	// 声明接任务人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*接任务人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明接单时间输入框
	private EditText ET_takeTime;
	// 声明订单状态下拉框
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null;
	/*订单状态管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();
	// 声明实时动态输入框
	private EditText ET_ssdt;
	// 声明用户评价输入框
	private EditText ET_evaluate;
	protected String carmera_path;
	/*要保存的代拿订单信息*/
	TakeOrder takeOrder = new TakeOrder();
	/*代拿订单管理业务逻辑层*/
	private TakeOrderService takeOrderService = new TakeOrderService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.takeorder_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加代拿订单");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_expressTakeObj = (Spinner) findViewById(R.id.Spinner_expressTakeObj);
		// 获取所有的代拿的快递
		try {
			expressTakeList = expressTakeService.QueryExpressTake(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int expressTakeCount = expressTakeList.size();
		expressTakeObj_ShowText = new String[expressTakeCount];
		for(int i=0;i<expressTakeCount;i++) { 
			expressTakeObj_ShowText[i] = expressTakeList.get(i).getTaskTitle();
		}
		// 将可选内容与ArrayAdapter连接起来
		expressTakeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, expressTakeObj_ShowText);
		// 设置下拉列表的风格
		expressTakeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_expressTakeObj.setAdapter(expressTakeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_expressTakeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				takeOrder.setExpressTakeObj(expressTakeList.get(arg2).getOrderId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_expressTakeObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的接任务人
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				takeOrder.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_takeTime = (EditText) findViewById(R.id.ET_takeTime);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// 获取所有的订单状态
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount];
		for(int i=0;i<orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i).getOrderStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				takeOrder.setOrderStateObj(orderStateList.get(arg2).getOrderStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		ET_ssdt = (EditText) findViewById(R.id.ET_ssdt);
		ET_evaluate = (EditText) findViewById(R.id.ET_evaluate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加代拿订单按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取接单时间*/ 
					if(ET_takeTime.getText().toString().equals("")) {
						Toast.makeText(TakeOrderAddActivity.this, "接单时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_takeTime.setFocusable(true);
						ET_takeTime.requestFocus();
						return;	
					}
					takeOrder.setTakeTime(ET_takeTime.getText().toString());
					/*验证获取实时动态*/ 
					if(ET_ssdt.getText().toString().equals("")) {
						Toast.makeText(TakeOrderAddActivity.this, "实时动态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_ssdt.setFocusable(true);
						ET_ssdt.requestFocus();
						return;	
					}
					takeOrder.setSsdt(ET_ssdt.getText().toString());
					/*验证获取用户评价*/ 
					if(ET_evaluate.getText().toString().equals("")) {
						Toast.makeText(TakeOrderAddActivity.this, "用户评价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_evaluate.setFocusable(true);
						ET_evaluate.requestFocus();
						return;	
					}
					takeOrder.setEvaluate(ET_evaluate.getText().toString());
					/*调用业务逻辑层上传代拿订单信息*/
					TakeOrderAddActivity.this.setTitle("正在上传代拿订单信息，稍等...");
					String result = takeOrderService.AddTakeOrder(takeOrder);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
