package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TakeOrderDAO;
import com.mobileserver.domain.TakeOrder;

import org.json.JSONStringer;

public class TakeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造代拿订单业务层对象*/
	private TakeOrderDAO takeOrderDAO = new TakeOrderDAO();

	/*默认构造函数*/
	public TakeOrderServlet() {
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
			/*获取查询代拿订单的参数信息*/
			int expressTakeObj = 0;
			if (request.getParameter("expressTakeObj") != null)
				expressTakeObj = Integer.parseInt(request.getParameter("expressTakeObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String takeTime = request.getParameter("takeTime");
			takeTime = takeTime == null ? "" : new String(request.getParameter(
					"takeTime").getBytes("iso-8859-1"), "UTF-8");
			int orderStateObj = 0;
			if (request.getParameter("orderStateObj") != null)
				orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));

			/*调用业务逻辑层执行代拿订单查询*/
			List<TakeOrder> takeOrderList = takeOrderDAO.QueryTakeOrder(expressTakeObj,userObj,takeTime,orderStateObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<TakeOrders>").append("\r\n");
			for (int i = 0; i < takeOrderList.size(); i++) {
				sb.append("	<TakeOrder>").append("\r\n")
				.append("		<orderId>")
				.append(takeOrderList.get(i).getOrderId())
				.append("</orderId>").append("\r\n")
				.append("		<expressTakeObj>")
				.append(takeOrderList.get(i).getExpressTakeObj())
				.append("</expressTakeObj>").append("\r\n")
				.append("		<userObj>")
				.append(takeOrderList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<takeTime>")
				.append(takeOrderList.get(i).getTakeTime())
				.append("</takeTime>").append("\r\n")
				.append("		<orderStateObj>")
				.append(takeOrderList.get(i).getOrderStateObj())
				.append("</orderStateObj>").append("\r\n")
				.append("		<ssdt>")
				.append(takeOrderList.get(i).getSsdt())
				.append("</ssdt>").append("\r\n")
				.append("		<evaluate>")
				.append(takeOrderList.get(i).getEvaluate())
				.append("</evaluate>").append("\r\n")
				.append("	</TakeOrder>").append("\r\n");
			}
			sb.append("</TakeOrders>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(TakeOrder takeOrder: takeOrderList) {
				  stringer.object();
			  stringer.key("orderId").value(takeOrder.getOrderId());
			  stringer.key("expressTakeObj").value(takeOrder.getExpressTakeObj());
			  stringer.key("userObj").value(takeOrder.getUserObj());
			  stringer.key("takeTime").value(takeOrder.getTakeTime());
			  stringer.key("orderStateObj").value(takeOrder.getOrderStateObj());
			  stringer.key("ssdt").value(takeOrder.getSsdt());
			  stringer.key("evaluate").value(takeOrder.getEvaluate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加代拿订单：获取代拿订单参数，参数保存到新建的代拿订单对象 */ 
			TakeOrder takeOrder = new TakeOrder();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			takeOrder.setOrderId(orderId);
			int expressTakeObj = Integer.parseInt(request.getParameter("expressTakeObj"));
			takeOrder.setExpressTakeObj(expressTakeObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setUserObj(userObj);
			String takeTime = new String(request.getParameter("takeTime").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setTakeTime(takeTime);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			takeOrder.setOrderStateObj(orderStateObj);
			String ssdt = new String(request.getParameter("ssdt").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setSsdt(ssdt);
			String evaluate = new String(request.getParameter("evaluate").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setEvaluate(evaluate);

			/* 调用业务层执行添加操作 */
			String result = takeOrderDAO.AddTakeOrder(takeOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除代拿订单：获取代拿订单的订单id*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*调用业务逻辑层执行删除操作*/
			String result = takeOrderDAO.DeleteTakeOrder(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新代拿订单之前先根据orderId查询某个代拿订单*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			TakeOrder takeOrder = takeOrderDAO.GetTakeOrder(orderId);

			// 客户端查询的代拿订单对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderId").value(takeOrder.getOrderId());
			  stringer.key("expressTakeObj").value(takeOrder.getExpressTakeObj());
			  stringer.key("userObj").value(takeOrder.getUserObj());
			  stringer.key("takeTime").value(takeOrder.getTakeTime());
			  stringer.key("orderStateObj").value(takeOrder.getOrderStateObj());
			  stringer.key("ssdt").value(takeOrder.getSsdt());
			  stringer.key("evaluate").value(takeOrder.getEvaluate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新代拿订单：获取代拿订单参数，参数保存到新建的代拿订单对象 */ 
			TakeOrder takeOrder = new TakeOrder();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			takeOrder.setOrderId(orderId);
			int expressTakeObj = Integer.parseInt(request.getParameter("expressTakeObj"));
			takeOrder.setExpressTakeObj(expressTakeObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setUserObj(userObj);
			String takeTime = new String(request.getParameter("takeTime").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setTakeTime(takeTime);
			int orderStateObj = Integer.parseInt(request.getParameter("orderStateObj"));
			takeOrder.setOrderStateObj(orderStateObj);
			String ssdt = new String(request.getParameter("ssdt").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setSsdt(ssdt);
			String evaluate = new String(request.getParameter("evaluate").getBytes("iso-8859-1"), "UTF-8");
			takeOrder.setEvaluate(evaluate);

			/* 调用业务层执行更新操作 */
			String result = takeOrderDAO.UpdateTakeOrder(takeOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
