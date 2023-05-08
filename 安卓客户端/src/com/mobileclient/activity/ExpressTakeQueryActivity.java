package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.ExpressTake;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

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
public class ExpressTakeQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明代拿任务输入框
	private EditText ET_taskTitle;
	// 声明物流公司下拉框
	private Spinner spinner_companyObj;
	private ArrayAdapter<String> companyObj_adapter;
	private static  String[] companyObj_ShowText  = null;
	private List<Company> companyList = null; 
	/*物流公司管理业务逻辑层*/
	private CompanyService companyService = new CompanyService();
	// 声明运单号码输入框
	private EditText ET_waybill;
	// 声明收货人输入框
	private EditText ET_receiverName;
	// 声明收货电话输入框
	private EditText ET_telephone;
	// 声明送达地址输入框
	private EditText ET_takePlace;
	// 声明代拿状态输入框
	private EditText ET_takeStateObj;
	// 声明任务发布人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	/*查询过滤条件保存到这个对象中*/
	private ExpressTake queryConditionExpressTake = new ExpressTake();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.expresstake_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置快递代拿查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_taskTitle = (EditText) findViewById(R.id.ET_taskTitle);
		spinner_companyObj = (Spinner) findViewById(R.id.Spinner_companyObj);
		// 获取所有的物流公司
		try {
			companyList = companyService.QueryCompany(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int companyCount = companyList.size();
		companyObj_ShowText = new String[companyCount+1];
		companyObj_ShowText[0] = "不限制";
		for(int i=1;i<=companyCount;i++) { 
			companyObj_ShowText[i] = companyList.get(i-1).getCompanyName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		companyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, companyObj_ShowText);
		// 设置物流公司下拉列表的风格
		companyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_companyObj.setAdapter(companyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_companyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionExpressTake.setCompanyObj(companyList.get(arg2-1).getCompanyId()); 
				else
					queryConditionExpressTake.setCompanyObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_companyObj.setVisibility(View.VISIBLE);
		ET_waybill = (EditText) findViewById(R.id.ET_waybill);
		ET_receiverName = (EditText) findViewById(R.id.ET_receiverName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_takePlace = (EditText) findViewById(R.id.ET_takePlace);
		ET_takeStateObj = (EditText) findViewById(R.id.ET_takeStateObj);
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
		// 设置任务发布人下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionExpressTake.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionExpressTake.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionExpressTake.setTaskTitle(ET_taskTitle.getText().toString());
					queryConditionExpressTake.setWaybill(ET_waybill.getText().toString());
					queryConditionExpressTake.setReceiverName(ET_receiverName.getText().toString());
					queryConditionExpressTake.setTelephone(ET_telephone.getText().toString());
					queryConditionExpressTake.setTakePlace(ET_takePlace.getText().toString());
					queryConditionExpressTake.setTakeStateObj(ET_takeStateObj.getText().toString());
					queryConditionExpressTake.setAddTime(ET_addTime.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionExpressTake", queryConditionExpressTake);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
