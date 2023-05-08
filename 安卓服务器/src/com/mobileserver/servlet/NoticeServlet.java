package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NoticeDAO;
import com.mobileserver.domain.Notice;

import org.json.JSONStringer;

public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������Ź���ҵ������*/
	private NoticeDAO noticeDAO = new NoticeDAO();

	/*Ĭ�Ϲ��캯��*/
	public NoticeServlet() {
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
			/*��ȡ��ѯ���Ź���Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String publishDate = request.getParameter("publishDate");
			publishDate = publishDate == null ? "" : new String(request.getParameter(
					"publishDate").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�����Ź����ѯ*/
			List<Notice> noticeList = noticeDAO.QueryNotice(title,publishDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Notices>").append("\r\n");
			for (int i = 0; i < noticeList.size(); i++) {
				sb.append("	<Notice>").append("\r\n")
				.append("		<noticeId>")
				.append(noticeList.get(i).getNoticeId())
				.append("</noticeId>").append("\r\n")
				.append("		<title>")
				.append(noticeList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(noticeList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<publishDate>")
				.append(noticeList.get(i).getPublishDate())
				.append("</publishDate>").append("\r\n")
				.append("	</Notice>").append("\r\n");
			}
			sb.append("</Notices>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Notice notice: noticeList) {
				  stringer.object();
			  stringer.key("noticeId").value(notice.getNoticeId());
			  stringer.key("title").value(notice.getTitle());
			  stringer.key("content").value(notice.getContent());
			  stringer.key("publishDate").value(notice.getPublishDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			notice.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			notice.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			notice.setPublishDate(publishDate);

			/* ����ҵ���ִ����Ӳ��� */
			String result = noticeDAO.AddNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����Ź��棺��ȡ���Ź���Ĺ���id*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = noticeDAO.DeleteNotice(noticeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������Ź���֮ǰ�ȸ���noticeId��ѯĳ�����Ź���*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			Notice notice = noticeDAO.GetNotice(noticeId);

			// �ͻ��˲�ѯ�����Ź�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("noticeId").value(notice.getNoticeId());
			  stringer.key("title").value(notice.getTitle());
			  stringer.key("content").value(notice.getContent());
			  stringer.key("publishDate").value(notice.getPublishDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������Ź��棺��ȡ���Ź���������������浽�½������Ź������ */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			notice.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			notice.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			notice.setPublishDate(publishDate);

			/* ����ҵ���ִ�и��²��� */
			String result = noticeDAO.UpdateNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
