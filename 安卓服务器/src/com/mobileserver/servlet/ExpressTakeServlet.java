package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ExpressTakeDAO;
import com.mobileserver.domain.ExpressTake;

import org.json.JSONStringer;

public class ExpressTakeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造快递代拿业务层对象*/
	private ExpressTakeDAO expressTakeDAO = new ExpressTakeDAO();

	/*默认构造函数*/
	public ExpressTakeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询快递代拿的参数信息*/
			String taskTitle = request.getParameter("taskTitle");
			taskTitle = taskTitle == null ? "" : new String(request.getParameter(
					"taskTitle").getBytes("iso-8859-1"), "UTF-8");
			int companyObj = 0;
			if (request.getParameter("companyObj") != null)
				companyObj = Integer.parseInt(request.getParameter("companyObj"));
			String waybill = request.getParameter("waybill");
			waybill = waybill == null ? "" : new String(request.getParameter(
					"waybill").getBytes("iso-8859-1"), "UTF-8");
			String receiverName = request.getParameter("receiverName");
			receiverName = receiverName == null ? "" : new String(request.getParameter(
					"receiverName").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			String takePlace = request.getParameter("takePlace");
			takePlace = takePlace == null ? "" : new String(request.getParameter(
					"takePlace").getBytes("iso-8859-1"), "UTF-8");
			String takeStateObj = request.getParameter("takeStateObj");
			takeStateObj = takeStateObj == null ? "" : new String(request.getParameter(
					"takeStateObj").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String addTime = request.getParameter("addTime");
			addTime = addTime == null ? "" : new String(request.getParameter(
					"addTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行快递代拿查询*/
			List<ExpressTake> expressTakeList = expressTakeDAO.QueryExpressTake(taskTitle,companyObj,waybill,receiverName,telephone,takePlace,takeStateObj,userObj,addTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ExpressTakes>").append("\r\n");
			for (int i = 0; i < expressTakeList.size(); i++) {
				sb.append("	<ExpressTake>").append("\r\n")
				.append("		<orderId>")
				.append(expressTakeList.get(i).getOrderId())
				.append("</orderId>").append("\r\n")
				.append("		<taskTitle>")
				.append(expressTakeList.get(i).getTaskTitle())
				.append("</taskTitle>").append("\r\n")
				.append("		<companyObj>")
				.append(expressTakeList.get(i).getCompanyObj())
				.append("</companyObj>").append("\r\n")
				.append("		<waybill>")
				.append(expressTakeList.get(i).getWaybill())
				.append("</waybill>").append("\r\n")
				.append("		<receiverName>")
				.append(expressTakeList.get(i).getReceiverName())
				.append("</receiverName>").append("\r\n")
				.append("		<telephone>")
				.append(expressTakeList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<receiveMemo>")
				.append(expressTakeList.get(i).getReceiveMemo())
				.append("</receiveMemo>").append("\r\n")
				.append("		<takePlace>")
				.append(expressTakeList.get(i).getTakePlace())
				.append("</takePlace>").append("\r\n")
				.append("		<giveMoney>")
				.append(expressTakeList.get(i).getGiveMoney())
				.append("</giveMoney>").append("\r\n")
				.append("		<takeStateObj>")
				.append(expressTakeList.get(i).getTakeStateObj())
				.append("</takeStateObj>").append("\r\n")
				.append("		<userObj>")
				.append(expressTakeList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<addTime>")
				.append(expressTakeList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("	</ExpressTake>").append("\r\n");
			}
			sb.append("</ExpressTakes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ExpressTake expressTake: expressTakeList) {
				  stringer.object();
			  stringer.key("orderId").value(expressTake.getOrderId());
			  stringer.key("taskTitle").value(expressTake.getTaskTitle());
			  stringer.key("companyObj").value(expressTake.getCompanyObj());
			  stringer.key("waybill").value(expressTake.getWaybill());
			  stringer.key("receiverName").value(expressTake.getReceiverName());
			  stringer.key("telephone").value(expressTake.getTelephone());
			  stringer.key("receiveMemo").value(expressTake.getReceiveMemo());
			  stringer.key("takePlace").value(expressTake.getTakePlace());
			  stringer.key("giveMoney").value(expressTake.getGiveMoney());
			  stringer.key("takeStateObj").value(expressTake.getTakeStateObj());
			  stringer.key("userObj").value(expressTake.getUserObj());
			  stringer.key("addTime").value(expressTake.getAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加快递代拿：获取快递代拿参数，参数保存到新建的快递代拿对象 */ 
			ExpressTake expressTake = new ExpressTake();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			expressTake.setOrderId(orderId);
			String taskTitle = new String(request.getParameter("taskTitle").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTaskTitle(taskTitle);
			int companyObj = Integer.parseInt(request.getParameter("companyObj"));
			expressTake.setCompanyObj(companyObj);
			String waybill = new String(request.getParameter("waybill").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setWaybill(waybill);
			String receiverName = new String(request.getParameter("receiverName").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setReceiverName(receiverName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTelephone(telephone);
			String receiveMemo = new String(request.getParameter("receiveMemo").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setReceiveMemo(receiveMemo);
			String takePlace = new String(request.getParameter("takePlace").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTakePlace(takePlace);
			float giveMoney = Float.parseFloat(request.getParameter("giveMoney"));
			expressTake.setGiveMoney(giveMoney);
			String takeStateObj = new String(request.getParameter("takeStateObj").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTakeStateObj(takeStateObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setAddTime(addTime);

			/* 调用业务层执行添加操作 */
			String result = expressTakeDAO.AddExpressTake(expressTake);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除快递代拿：获取快递代拿的订单id*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*调用业务逻辑层执行删除操作*/
			String result = expressTakeDAO.DeleteExpressTake(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新快递代拿之前先根据orderId查询某个快递代拿*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			ExpressTake expressTake = expressTakeDAO.GetExpressTake(orderId);

			// 客户端查询的快递代拿对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderId").value(expressTake.getOrderId());
			  stringer.key("taskTitle").value(expressTake.getTaskTitle());
			  stringer.key("companyObj").value(expressTake.getCompanyObj());
			  stringer.key("waybill").value(expressTake.getWaybill());
			  stringer.key("receiverName").value(expressTake.getReceiverName());
			  stringer.key("telephone").value(expressTake.getTelephone());
			  stringer.key("receiveMemo").value(expressTake.getReceiveMemo());
			  stringer.key("takePlace").value(expressTake.getTakePlace());
			  stringer.key("giveMoney").value(expressTake.getGiveMoney());
			  stringer.key("takeStateObj").value(expressTake.getTakeStateObj());
			  stringer.key("userObj").value(expressTake.getUserObj());
			  stringer.key("addTime").value(expressTake.getAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新快递代拿：获取快递代拿参数，参数保存到新建的快递代拿对象 */ 
			ExpressTake expressTake = new ExpressTake();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			expressTake.setOrderId(orderId);
			String taskTitle = new String(request.getParameter("taskTitle").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTaskTitle(taskTitle);
			int companyObj = Integer.parseInt(request.getParameter("companyObj"));
			expressTake.setCompanyObj(companyObj);
			String waybill = new String(request.getParameter("waybill").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setWaybill(waybill);
			String receiverName = new String(request.getParameter("receiverName").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setReceiverName(receiverName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTelephone(telephone);
			String receiveMemo = new String(request.getParameter("receiveMemo").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setReceiveMemo(receiveMemo);
			String takePlace = new String(request.getParameter("takePlace").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTakePlace(takePlace);
			float giveMoney = Float.parseFloat(request.getParameter("giveMoney"));
			expressTake.setGiveMoney(giveMoney);
			String takeStateObj = new String(request.getParameter("takeStateObj").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setTakeStateObj(takeStateObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setUserObj(userObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			expressTake.setAddTime(addTime);

			/* 调用业务层执行更新操作 */
			String result = expressTakeDAO.UpdateExpressTake(expressTake);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
