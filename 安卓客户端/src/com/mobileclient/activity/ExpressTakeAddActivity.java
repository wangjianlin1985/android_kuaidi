package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.ExpressTake;
import com.mobileclient.service.ExpressTakeService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class ExpressTakeAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
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
	// 声明收货备注输入框
	private EditText ET_receiveMemo;
	// 声明送达地址输入框
	private EditText ET_takePlace;
	// 声明代拿报酬输入框
	private EditText ET_giveMoney;
	// 声明代拿状态输入框
	private EditText ET_takeStateObj;
	// 声明任务发布人下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*任务发布人管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明发布时间输入框
	private EditText ET_addTime;
	protected String carmera_path;
	/*要保存的快递代拿信息*/
	ExpressTake expressTake = new ExpressTake();
	/*快递代拿管理业务逻辑层*/
	private ExpressTakeService expressTakeService = new ExpressTakeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.expresstake_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加快递代拿");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_taskTitle = (EditText) findViewById(R.id.ET_taskTitle);
		spinner_companyObj = (Spinner) findViewById(R.id.Spinner_companyObj);
		// 获取所有的物流公司
		try {
			companyList = companyService.QueryCompany(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int companyCount = companyList.size();
		companyObj_ShowText = new String[companyCount];
		for(int i=0;i<companyCount;i++) { 
			companyObj_ShowText[i] = companyList.get(i).getCompanyName();
		}
		// 将可选内容与ArrayAdapter连接起来
		companyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, companyObj_ShowText);
		// 设置下拉列表的风格
		companyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_companyObj.setAdapter(companyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_companyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				expressTake.setCompanyObj(companyList.get(arg2).getCompanyId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_companyObj.setVisibility(View.VISIBLE);
		ET_waybill = (EditText) findViewById(R.id.ET_waybill);
		ET_receiverName = (EditText) findViewById(R.id.ET_receiverName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_receiveMemo = (EditText) findViewById(R.id.ET_receiveMemo);
		ET_takePlace = (EditText) findViewById(R.id.ET_takePlace);
		ET_giveMoney = (EditText) findViewById(R.id.ET_giveMoney);
		ET_takeStateObj = (EditText) findViewById(R.id.ET_takeStateObj);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的任务发布人
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
				expressTake.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加快递代拿按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取代拿任务*/ 
					if(ET_taskTitle.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "代拿任务输入不能为空!", Toast.LENGTH_LONG).show();
						ET_taskTitle.setFocusable(true);
						ET_taskTitle.requestFocus();
						return;	
					}
					expressTake.setTaskTitle(ET_taskTitle.getText().toString());
					/*验证获取运单号码*/ 
					if(ET_waybill.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "运单号码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_waybill.setFocusable(true);
						ET_waybill.requestFocus();
						return;	
					}
					expressTake.setWaybill(ET_waybill.getText().toString());
					/*验证获取收货人*/ 
					if(ET_receiverName.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "收货人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_receiverName.setFocusable(true);
						ET_receiverName.requestFocus();
						return;	
					}
					expressTake.setReceiverName(ET_receiverName.getText().toString());
					/*验证获取收货电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "收货电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					expressTake.setTelephone(ET_telephone.getText().toString());
					/*验证获取收货备注*/ 
					if(ET_receiveMemo.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "收货备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_receiveMemo.setFocusable(true);
						ET_receiveMemo.requestFocus();
						return;	
					}
					expressTake.setReceiveMemo(ET_receiveMemo.getText().toString());
					/*验证获取送达地址*/ 
					if(ET_takePlace.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "送达地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_takePlace.setFocusable(true);
						ET_takePlace.requestFocus();
						return;	
					}
					expressTake.setTakePlace(ET_takePlace.getText().toString());
					/*验证获取代拿报酬*/ 
					if(ET_giveMoney.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "代拿报酬输入不能为空!", Toast.LENGTH_LONG).show();
						ET_giveMoney.setFocusable(true);
						ET_giveMoney.requestFocus();
						return;	
					}
					expressTake.setGiveMoney(Float.parseFloat(ET_giveMoney.getText().toString()));
					/*验证获取代拿状态*/ 
					if(ET_takeStateObj.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "代拿状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_takeStateObj.setFocusable(true);
						ET_takeStateObj.requestFocus();
						return;	
					}
					expressTake.setTakeStateObj(ET_takeStateObj.getText().toString());
					/*验证获取发布时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(ExpressTakeAddActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					expressTake.setAddTime(ET_addTime.getText().toString());
					/*调用业务逻辑层上传快递代拿信息*/
					ExpressTakeAddActivity.this.setTitle("正在上传快递代拿信息，稍等...");
					String result = expressTakeService.AddExpressTake(expressTake);
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
