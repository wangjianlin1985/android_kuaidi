package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.TakeOrder;
import com.mobileclient.domain.ExpressTake;
import com.mobileclient.service.ExpressTakeService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class TakeOrderQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明代拿的快递下拉框
	private Spinner spinner_expressTakeObj;
	private ArrayAdapter<String> expressTakeObj_adapter;
	private static  String[] expressTakeObj_ShowText  = null;
	private List<ExpressTake> expressTakeList = null; 
	/*快递代拿管理业务逻辑层*/
	private ExpressTakeService expressTakeService = new ExpressTakeService();
	// 声明接任务人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
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
	/*查询过滤条件保存到这个对象中*/
	private TakeOrder queryConditionTakeOrder = new TakeOrder();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.takeorder_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置代拿订单查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_expressTakeObj = (Spinner) findViewById(R.id.Spinner_expressTakeObj);
		// 获取所有的快递代拿
		try {
			expressTakeList = expressTakeService.QueryExpressTake(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int expressTakeCount = expressTakeList.size();
		expressTakeObj_ShowText = new String[expressTakeCount+1];
		expressTakeObj_ShowText[0] = "不限制";
		for(int i=1;i<=expressTakeCount;i++) { 
			expressTakeObj_ShowText[i] = expressTakeList.get(i-1).getTaskTitle();
		} 
		// 将可选内容与ArrayAdapter连接起来
		expressTakeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, expressTakeObj_ShowText);
		// 设置代拿的快递下拉列表的风格
		expressTakeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_expressTakeObj.setAdapter(expressTakeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_expressTakeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTakeOrder.setExpressTakeObj(expressTakeList.get(arg2-1).getOrderId()); 
				else
					queryConditionTakeOrder.setExpressTakeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_expressTakeObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置接任务人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTakeOrder.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionTakeOrder.setUserObj("");
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
		orderStateObj_ShowText = new String[orderStateCount+1];
		orderStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i-1).getOrderStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置订单状态下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionTakeOrder.setOrderStateObj(orderStateList.get(arg2-1).getOrderStateId()); 
				else
					queryConditionTakeOrder.setOrderStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionTakeOrder.setTakeTime(ET_takeTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionTakeOrder", queryConditionTakeOrder);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
