package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderStateDAO;
import com.mobileserver.domain.OrderState;

import org.json.JSONStringer;

public class OrderStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造订单状态业务层对象*/
	private OrderStateDAO orderStateDAO = new OrderStateDAO();

	/*默认构造函数*/
	public OrderStateServlet() {
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
			/*获取查询订单状态的参数信息*/

			/*调用业务逻辑层执行订单状态查询*/
			List<OrderState> orderStateList = orderStateDAO.QueryOrderState();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderStates>").append("\r\n");
			for (int i = 0; i < orderStateList.size(); i++) {
				sb.append("	<OrderState>").append("\r\n")
				.append("		<orderStateId>")
				.append(orderStateList.get(i).getOrderStateId())
				.append("</orderStateId>").append("\r\n")
				.append("		<orderStateName>")
				.append(orderStateList.get(i).getOrderStateName())
				.append("</orderStateName>").append("\r\n")
				.append("	</OrderState>").append("\r\n");
			}
			sb.append("</OrderStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderState orderState: orderStateList) {
				  stringer.object();
			  stringer.key("orderStateId").value(orderState.getOrderStateId());
			  stringer.key("orderStateName").value(orderState.getOrderStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加订单状态：获取订单状态参数，参数保存到新建的订单状态对象 */ 
			OrderState orderState = new OrderState();
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			orderState.setOrderStateId(orderStateId);
			String orderStateName = new String(request.getParameter("orderStateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setOrderStateName(orderStateName);

			/* 调用业务层执行添加操作 */
			String result = orderStateDAO.AddOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除订单状态：获取订单状态的订单状态id*/
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = orderStateDAO.DeleteOrderState(orderStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新订单状态之前先根据orderStateId查询某个订单状态*/
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			OrderState orderState = orderStateDAO.GetOrderState(orderStateId);

			// 客户端查询的订单状态对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderStateId").value(orderState.getOrderStateId());
			  stringer.key("orderStateName").value(orderState.getOrderStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新订单状态：获取订单状态参数，参数保存到新建的订单状态对象 */ 
			OrderState orderState = new OrderState();
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			orderState.setOrderStateId(orderStateId);
			String orderStateName = new String(request.getParameter("orderStateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setOrderStateName(orderStateName);

			/* 调用业务层执行更新操作 */
			String result = orderStateDAO.UpdateOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
