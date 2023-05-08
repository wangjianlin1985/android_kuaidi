package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CompanyService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ExpressTakeSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public ExpressTakeSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.expresstake_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_taskTitle = (TextView)convertView.findViewById(R.id.tv_taskTitle);
	  holder.tv_companyObj = (TextView)convertView.findViewById(R.id.tv_companyObj);
	  holder.tv_waybill = (TextView)convertView.findViewById(R.id.tv_waybill);
	  holder.tv_receiverName = (TextView)convertView.findViewById(R.id.tv_receiverName);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_receiveMemo = (TextView)convertView.findViewById(R.id.tv_receiveMemo);
	  holder.tv_takePlace = (TextView)convertView.findViewById(R.id.tv_takePlace);
	  holder.tv_giveMoney = (TextView)convertView.findViewById(R.id.tv_giveMoney);
	  holder.tv_takeStateObj = (TextView)convertView.findViewById(R.id.tv_takeStateObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_taskTitle.setText("代拿任务：" + mData.get(position).get("taskTitle").toString());
	  holder.tv_companyObj.setText("物流公司：" + (new CompanyService()).GetCompany(Integer.parseInt(mData.get(position).get("companyObj").toString())).getCompanyName());
	  holder.tv_waybill.setText("运单号码：" + mData.get(position).get("waybill").toString());
	  holder.tv_receiverName.setText("收货人：" + mData.get(position).get("receiverName").toString());
	  holder.tv_telephone.setText("收货电话：" + mData.get(position).get("telephone").toString());
	  holder.tv_receiveMemo.setText("收货备注：" + mData.get(position).get("receiveMemo").toString());
	  holder.tv_takePlace.setText("送达地址：" + mData.get(position).get("takePlace").toString());
	  holder.tv_giveMoney.setText("代拿报酬：" + mData.get(position).get("giveMoney").toString());
	  holder.tv_takeStateObj.setText("代拿状态：" + mData.get(position).get("takeStateObj").toString());
	  holder.tv_userObj.setText("任务发布人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_addTime.setText("发布时间：" + mData.get(position).get("addTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_taskTitle;
    	TextView tv_companyObj;
    	TextView tv_waybill;
    	TextView tv_receiverName;
    	TextView tv_telephone;
    	TextView tv_receiveMemo;
    	TextView tv_takePlace;
    	TextView tv_giveMoney;
    	TextView tv_takeStateObj;
    	TextView tv_userObj;
    	TextView tv_addTime;
    }
} 
