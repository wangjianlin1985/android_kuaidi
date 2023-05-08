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

	/*���충��״̬ҵ������*/
	private OrderStateDAO orderStateDAO = new OrderStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderStateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ����״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�ж���״̬��ѯ*/
			List<OrderState> orderStateList = orderStateDAO.QueryOrderState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӷ���״̬����ȡ����״̬�������������浽�½��Ķ���״̬���� */ 
			OrderState orderState = new OrderState();
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			orderState.setOrderStateId(orderStateId);
			String orderStateName = new String(request.getParameter("orderStateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setOrderStateName(orderStateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderStateDAO.AddOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������״̬����ȡ����״̬�Ķ���״̬id*/
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderStateDAO.DeleteOrderState(orderStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¶���״̬֮ǰ�ȸ���orderStateId��ѯĳ������״̬*/
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			OrderState orderState = orderStateDAO.GetOrderState(orderStateId);

			// �ͻ��˲�ѯ�Ķ���״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¶���״̬����ȡ����״̬�������������浽�½��Ķ���״̬���� */ 
			OrderState orderState = new OrderState();
			int orderStateId = Integer.parseInt(request.getParameter("orderStateId"));
			orderState.setOrderStateId(orderStateId);
			String orderStateName = new String(request.getParameter("orderStateName").getBytes("iso-8859-1"), "UTF-8");
			orderState.setOrderStateName(orderStateName);

			/* ����ҵ���ִ�и��²��� */
			String result = orderStateDAO.UpdateOrderState(orderState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
