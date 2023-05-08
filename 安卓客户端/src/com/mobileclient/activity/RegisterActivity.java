package com.mobileclient.activity;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileclient.util.HttpUtil;

public class RegisterActivity extends Activity {
	// 声明登录、取消按钮
	private Button Btn1,Btn2;
	// 声明用户名、密码输入框
	private EditText userName,pwd,phone,address;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("无线系统-注册");
		// 设置当前Activity界面布局
		setContentView(R.layout.register);
		// 通过findViewById方法实例化组件
		Btn1 = (Button)findViewById(R.id.registerButton1);
		// 通过findViewById方法实例化组件
		Btn2 = (Button)findViewById(R.id.registerButton2);
		// 通过findViewById方法实例化组件
		userName = (EditText)findViewById(R.id.userName);
		// 通过findViewById方法实例化组件
		pwd = (EditText)findViewById(R.id.pwd);
		phone = (EditText)findViewById(R.id.phone);
		address = (EditText)findViewById(R.id.address);
		
		Btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					
					if(userName.getText()!=null&&pwd.getText()!=null&&phone.getText()!=null&&address.getText()!=null){
							String url = HttpUtil.BASE_URL
									+ "RegisterServlet?userName="
									+ URLEncoder.encode(
											URLEncoder.encode(userName.getText().toString(), "UTF-8"), "UTF-8")+"&password="
											+ URLEncoder.encode(
											URLEncoder.encode(pwd.getText().toString(), "UTF-8"), "UTF-8")+"&phone="
													+ URLEncoder.encode(
															URLEncoder.encode(phone.getText().toString(), "UTF-8"), "UTF-8")+"&address="
																	+ URLEncoder.encode(
																			URLEncoder.encode(address.getText().toString(), "UTF-8"), "UTF-8");
							// 查询返回结果
							String result = HttpUtil.queryStringForPost(url);
							if(result.equals("1")){
								Toast.makeText(getApplicationContext(), "注册成功", 1).show();
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this,
										LoginActivity.class);
								startActivity(intent);
							}else if(result.equals("2")){
								Toast.makeText(getApplicationContext(), "用户名重复", 1).show();
							}else{
								Toast.makeText(getApplicationContext(), "注册失败", 1).show();
							}

					}else{
						Toast.makeText(getApplicationContext(), "不能为空", 1).show();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userName.setText("");
				pwd.setText("");
				phone.setText("");
				address.setText("");
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");
		menu.add(0, 2, 2, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入
			
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true;
	}
}