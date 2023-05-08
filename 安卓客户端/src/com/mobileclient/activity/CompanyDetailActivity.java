package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
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
public class CompanyDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明公司id控件
	private TextView TV_companyId;
	// 声明公司名称控件
	private TextView TV_companyName;
	/* 要保存的物流公司信息 */
	Company company = new Company(); 
	/* 物流公司管理业务逻辑层 */
	private CompanyService companyService = new CompanyService();
	private int companyId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.company_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看物流公司详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_companyId = (TextView) findViewById(R.id.TV_companyId);
		TV_companyName = (TextView) findViewById(R.id.TV_companyName);
		Bundle extras = this.getIntent().getExtras();
		companyId = extras.getInt("companyId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CompanyDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    company = companyService.GetCompany(companyId); 
		this.TV_companyId.setText(company.getCompanyId() + "");
		this.TV_companyName.setText(company.getCompanyName());
	} 
}
