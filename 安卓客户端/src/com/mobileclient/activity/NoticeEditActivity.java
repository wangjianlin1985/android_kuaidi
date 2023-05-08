package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Notice;
import com.mobileclient.service.NoticeService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class NoticeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明公告idTextView
	private TextView TV_noticeId;
	// 声明标题输入框
	private EditText ET_title;
	// 声明公告内容输入框
	private EditText ET_content;
	// 声明发布时间输入框
	private EditText ET_publishDate;
	protected String carmera_path;
	/*要保存的新闻公告信息*/
	Notice notice = new Notice();
	/*新闻公告管理业务逻辑层*/
	private NoticeService noticeService = new NoticeService();

	private int noticeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑新闻公告信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_noticeId = (TextView) findViewById(R.id.TV_noticeId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_publishDate = (EditText) findViewById(R.id.ET_publishDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		noticeId = extras.getInt("noticeId");
		/*单击修改新闻公告按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(NoticeEditActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					notice.setTitle(ET_title.getText().toString());
					/*验证获取公告内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(NoticeEditActivity.this, "公告内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					notice.setContent(ET_content.getText().toString());
					/*验证获取发布时间*/ 
					if(ET_publishDate.getText().toString().equals("")) {
						Toast.makeText(NoticeEditActivity.this, "发布时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_publishDate.setFocusable(true);
						ET_publishDate.requestFocus();
						return;	
					}
					notice.setPublishDate(ET_publishDate.getText().toString());
					/*调用业务逻辑层上传新闻公告信息*/
					NoticeEditActivity.this.setTitle("正在更新新闻公告信息，稍等...");
					String result = noticeService.UpdateNotice(notice);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    notice = noticeService.GetNotice(noticeId);
		this.TV_noticeId.setText(noticeId+"");
		this.ET_title.setText(notice.getTitle());
		this.ET_content.setText(notice.getContent());
		this.ET_publishDate.setText(notice.getPublishDate());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
