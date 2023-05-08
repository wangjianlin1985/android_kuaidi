package com.mobileclient.activity;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author 作者 :Duncan Wei
 * @version 创建时间：2013-12-9 下午01:43:42
 * 类说明
 */

public class MyProgressDialog extends ProgressDialog {

	public MyProgressDialog(Context context) {
		super(context);
	}
	public static MyProgressDialog getInstance(Context context)
	{
		MyProgressDialog dialog=new MyProgressDialog(context);
			dialog.setMessage("请稍候...");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
		return dialog;
	}

}
