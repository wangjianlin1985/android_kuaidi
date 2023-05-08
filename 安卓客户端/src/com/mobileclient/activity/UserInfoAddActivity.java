package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class UserInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明用户类型输入框
	private EditText ET_userType;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_gender;
	// 出版出生日期控件
	private DatePicker dp_birthDate;
	// 声明用户照片图片框控件
	private ImageView iv_userPhoto;
	private Button btn_userPhoto;
	protected int REQ_CODE_SELECT_IMAGE_userPhoto = 1;
	private int REQ_CODE_CAMERA_userPhoto = 2;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明邮箱输入框
	private EditText ET_email;
	// 声明家庭地址输入框
	private EditText ET_address;
	// 声明认证文件相关控件
	private TextView TV_authFile;
	private Button btn_authFile;
	private int REQ_CODE_SELECT_FILE_authFile = 3;
	// 声明审核状态输入框
	private EditText ET_shenHeState;
	// 声明注册时间输入框
	private EditText ET_regTime;
	protected String carmera_path;
	/*要保存的用户信息*/
	UserInfo userInfo = new UserInfo();
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加用户");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_userType = (EditText) findViewById(R.id.ET_userType);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_gender = (EditText) findViewById(R.id.ET_gender);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_userPhoto);
			}
		});
		btn_userPhoto = (Button) findViewById(R.id.btn_userPhoto);
		btn_userPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_userPhoto);  
			}
		});
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_email = (EditText) findViewById(R.id.ET_email);
		ET_address = (EditText) findViewById(R.id.ET_address);
		TV_authFile = (TextView) findViewById(R.id.TV_authFile);
		btn_authFile = (Button) findViewById(R.id.btn_authFile);
		btn_authFile.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(UserInfoAddActivity.this,fileListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_FILE_authFile);
			}
		});
		ET_shenHeState = (EditText) findViewById(R.id.ET_shenHeState);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加用户按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取用户名*/
					if(ET_user_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "用户名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_user_name.setFocusable(true);
						ET_user_name.requestFocus();
						return;
					}
					userInfo.setUser_name(ET_user_name.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*验证获取用户类型*/ 
					if(ET_userType.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "用户类型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_userType.setFocusable(true);
						ET_userType.requestFocus();
						return;	
					}
					userInfo.setUserType(ET_userType.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_gender.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_gender.setFocusable(true);
						ET_gender.requestFocus();
						return;	
					}
					userInfo.setGender(ET_gender.getText().toString());
					/*获取出生日期*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					userInfo.setBirthDate(new Timestamp(birthDate.getTime()));
					if(userInfo.getUserPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						UserInfoAddActivity.this.setTitle("正在上传图片，稍等...");
						String userPhoto = HttpUtil.uploadFile(userInfo.getUserPhoto());
						UserInfoAddActivity.this.setTitle("图片上传完毕！");
						userInfo.setUserPhoto(userPhoto);
					} else {
						userInfo.setUserPhoto("upload/noimage.jpg");
					}
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*验证获取邮箱*/ 
					if(ET_email.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "邮箱输入不能为空!", Toast.LENGTH_LONG).show();
						ET_email.setFocusable(true);
						ET_email.requestFocus();
						return;	
					}
					userInfo.setEmail(ET_email.getText().toString());
					/*验证获取家庭地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "家庭地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					if(userInfo.getAuthFile() != null) {
						//如果文件地址不为空，说明用户选择了文件，这时需要连接服务器上传文件
						UserInfoAddActivity.this.setTitle("正在上传文件，稍等...");
						String authFile = HttpUtil.uploadFile(userInfo.getAuthFile());
						UserInfoAddActivity.this.setTitle("文件上传完毕！");
						userInfo.setAuthFile(authFile); 
					} else {
						Toast.makeText(getApplicationContext(), "请先选择认证文件", 1).show();
						return;
					}
					/*验证获取审核状态*/ 
					if(ET_shenHeState.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "审核状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shenHeState.setFocusable(true);
						ET_shenHeState.requestFocus();
						return;	
					}
					userInfo.setShenHeState(ET_shenHeState.getText().toString());
					/*验证获取注册时间*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(UserInfoAddActivity.this, "注册时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					userInfo.setRegTime(ET_regTime.getText().toString());
					/*调用业务逻辑层上传用户信息*/
					UserInfoAddActivity.this.setTitle("正在上传用户信息，稍等...");
					String result = userInfoService.AddUserInfo(userInfo);
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
		if (requestCode == REQ_CODE_CAMERA_userPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_userPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_userPhoto.setImageBitmap(booImageBm);
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setUserPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_userPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_userPhoto.setImageBitmap(bm); 
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setUserPhoto(filename); 
		}
		if(requestCode == REQ_CODE_SELECT_FILE_authFile && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			this.TV_authFile.setText(filepath); 
			userInfo.setAuthFile(filename);
		}

	}
}
