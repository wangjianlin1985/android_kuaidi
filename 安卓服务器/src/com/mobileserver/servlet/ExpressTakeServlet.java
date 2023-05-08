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

	/*�����ݴ���ҵ������*/
	private ExpressTakeDAO expressTakeDAO = new ExpressTakeDAO();

	/*Ĭ�Ϲ��캯��*/
	public ExpressTakeServlet() {
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
			/*��ȡ��ѯ��ݴ��õĲ�����Ϣ*/
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

			/*����ҵ���߼���ִ�п�ݴ��ò�ѯ*/
			List<ExpressTake> expressTakeList = expressTakeDAO.QueryExpressTake(taskTitle,companyObj,waybill,receiverName,telephone,takePlace,takeStateObj,userObj,addTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿ�ݴ��ã���ȡ��ݴ��ò������������浽�½��Ŀ�ݴ��ö��� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = expressTakeDAO.AddExpressTake(expressTake);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ݴ��ã���ȡ��ݴ��õĶ���id*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = expressTakeDAO.DeleteExpressTake(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿�ݴ���֮ǰ�ȸ���orderId��ѯĳ����ݴ���*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			ExpressTake expressTake = expressTakeDAO.GetExpressTake(orderId);

			// �ͻ��˲�ѯ�Ŀ�ݴ��ö��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿�ݴ��ã���ȡ��ݴ��ò������������浽�½��Ŀ�ݴ��ö��� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = expressTakeDAO.UpdateExpressTake(expressTake);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
