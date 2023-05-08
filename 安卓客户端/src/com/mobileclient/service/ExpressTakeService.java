package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ExpressTake;
import com.mobileclient.util.HttpUtil;

/*快递代拿管理业务逻辑层*/
public class ExpressTakeService {
	/* 添加快递代拿 */
	public String AddExpressTake(ExpressTake expressTake) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", expressTake.getOrderId() + "");
		params.put("taskTitle", expressTake.getTaskTitle());
		params.put("companyObj", expressTake.getCompanyObj() + "");
		params.put("waybill", expressTake.getWaybill());
		params.put("receiverName", expressTake.getReceiverName());
		params.put("telephone", expressTake.getTelephone());
		params.put("receiveMemo", expressTake.getReceiveMemo());
		params.put("takePlace", expressTake.getTakePlace());
		params.put("giveMoney", expressTake.getGiveMoney() + "");
		params.put("takeStateObj", expressTake.getTakeStateObj());
		params.put("userObj", expressTake.getUserObj());
		params.put("addTime", expressTake.getAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExpressTakeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询快递代拿 */
	public List<ExpressTake> QueryExpressTake(ExpressTake queryConditionExpressTake) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ExpressTakeServlet?action=query";
		if(queryConditionExpressTake != null) {
			urlString += "&taskTitle=" + URLEncoder.encode(queryConditionExpressTake.getTaskTitle(), "UTF-8") + "";
			urlString += "&companyObj=" + queryConditionExpressTake.getCompanyObj();
			urlString += "&waybill=" + URLEncoder.encode(queryConditionExpressTake.getWaybill(), "UTF-8") + "";
			urlString += "&receiverName=" + URLEncoder.encode(queryConditionExpressTake.getReceiverName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionExpressTake.getTelephone(), "UTF-8") + "";
			urlString += "&takePlace=" + URLEncoder.encode(queryConditionExpressTake.getTakePlace(), "UTF-8") + "";
			urlString += "&takeStateObj=" + URLEncoder.encode(queryConditionExpressTake.getTakeStateObj(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionExpressTake.getUserObj(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionExpressTake.getAddTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ExpressTakeListHandler expressTakeListHander = new ExpressTakeListHandler();
		xr.setContentHandler(expressTakeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ExpressTake> expressTakeList = expressTakeListHander.getExpressTakeList();
		return expressTakeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ExpressTake> expressTakeList = new ArrayList<ExpressTake>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ExpressTake expressTake = new ExpressTake();
				expressTake.setOrderId(object.getInt("orderId"));
				expressTake.setTaskTitle(object.getString("taskTitle"));
				expressTake.setCompanyObj(object.getInt("companyObj"));
				expressTake.setWaybill(object.getString("waybill"));
				expressTake.setReceiverName(object.getString("receiverName"));
				expressTake.setTelephone(object.getString("telephone"));
				expressTake.setReceiveMemo(object.getString("receiveMemo"));
				expressTake.setTakePlace(object.getString("takePlace"));
				expressTake.setGiveMoney((float) object.getDouble("giveMoney"));
				expressTake.setTakeStateObj(object.getString("takeStateObj"));
				expressTake.setUserObj(object.getString("userObj"));
				expressTake.setAddTime(object.getString("addTime"));
				expressTakeList.add(expressTake);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expressTakeList;
	}

	/* 更新快递代拿 */
	public String UpdateExpressTake(ExpressTake expressTake) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", expressTake.getOrderId() + "");
		params.put("taskTitle", expressTake.getTaskTitle());
		params.put("companyObj", expressTake.getCompanyObj() + "");
		params.put("waybill", expressTake.getWaybill());
		params.put("receiverName", expressTake.getReceiverName());
		params.put("telephone", expressTake.getTelephone());
		params.put("receiveMemo", expressTake.getReceiveMemo());
		params.put("takePlace", expressTake.getTakePlace());
		params.put("giveMoney", expressTake.getGiveMoney() + "");
		params.put("takeStateObj", expressTake.getTakeStateObj());
		params.put("userObj", expressTake.getUserObj());
		params.put("addTime", expressTake.getAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExpressTakeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除快递代拿 */
	public String DeleteExpressTake(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExpressTakeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "快递代拿信息删除失败!";
		}
	}

	/* 根据订单id获取快递代拿对象 */
	public ExpressTake GetExpressTake(int orderId)  {
		List<ExpressTake> expressTakeList = new ArrayList<ExpressTake>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ExpressTakeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ExpressTake expressTake = new ExpressTake();
				expressTake.setOrderId(object.getInt("orderId"));
				expressTake.setTaskTitle(object.getString("taskTitle"));
				expressTake.setCompanyObj(object.getInt("companyObj"));
				expressTake.setWaybill(object.getString("waybill"));
				expressTake.setReceiverName(object.getString("receiverName"));
				expressTake.setTelephone(object.getString("telephone"));
				expressTake.setReceiveMemo(object.getString("receiveMemo"));
				expressTake.setTakePlace(object.getString("takePlace"));
				expressTake.setGiveMoney((float) object.getDouble("giveMoney"));
				expressTake.setTakeStateObj(object.getString("takeStateObj"));
				expressTake.setUserObj(object.getString("userObj"));
				expressTake.setAddTime(object.getString("addTime"));
				expressTakeList.add(expressTake);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = expressTakeList.size();
		if(size>0) return expressTakeList.get(0); 
		else return null; 
	}
}
