package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ExpressTakeService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.OrderStateService;
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

public class TakeOrderSimpleAdapter extends SimpleAdapter { 
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

    public TakeOrderSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.takeorder_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_expressTakeObj = (TextView)convertView.findViewById(R.id.tv_expressTakeObj);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_takeTime = (TextView)convertView.findViewById(R.id.tv_takeTime);
	  holder.tv_orderStateObj = (TextView)convertView.findViewById(R.id.tv_orderStateObj);
	  holder.tv_ssdt = (TextView)convertView.findViewById(R.id.tv_ssdt);
	  holder.tv_evaluate = (TextView)convertView.findViewById(R.id.tv_evaluate);
	  /*设置各个控件的展示内容*/
	  holder.tv_expressTakeObj.setText("代拿的快递：" + (new ExpressTakeService()).GetExpressTake(Integer.parseInt(mData.get(position).get("expressTakeObj").toString())).getTaskTitle());
	  holder.tv_userObj.setText("接任务人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_takeTime.setText("接单时间：" + mData.get(position).get("takeTime").toString());
	  holder.tv_orderStateObj.setText("订单状态：" + (new OrderStateService()).GetOrderState(Integer.parseInt(mData.get(position).get("orderStateObj").toString())).getOrderStateName());
	  holder.tv_ssdt.setText("实时动态：" + mData.get(position).get("ssdt").toString());
	  holder.tv_evaluate.setText("用户评价：" + mData.get(position).get("evaluate").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_expressTakeObj;
    	TextView tv_userObj;
    	TextView tv_takeTime;
    	TextView tv_orderStateObj;
    	TextView tv_ssdt;
    	TextView tv_evaluate;
    }
} 
