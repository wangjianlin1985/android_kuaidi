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

	/*构造新闻公告业务层对象*/
	private NoticeDAO noticeDAO = new NoticeDAO();

	/*默认构造函数*/
	public NoticeServlet() {
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
			/*获取查询新闻公告的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String publishDate = request.getParameter("publishDate");
			publishDate = publishDate == null ? "" : new String(request.getParameter(
					"publishDate").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行新闻公告查询*/
			List<Notice> noticeList = noticeDAO.QueryNotice(title,publishDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加新闻公告：获取新闻公告参数，参数保存到新建的新闻公告对象 */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			notice.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			notice.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			notice.setPublishDate(publishDate);

			/* 调用业务层执行添加操作 */
			String result = noticeDAO.AddNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除新闻公告：获取新闻公告的公告id*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = noticeDAO.DeleteNotice(noticeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新新闻公告之前先根据noticeId查询某个新闻公告*/
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			Notice notice = noticeDAO.GetNotice(noticeId);

			// 客户端查询的新闻公告对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新新闻公告：获取新闻公告参数，参数保存到新建的新闻公告对象 */ 
			Notice notice = new Notice();
			int noticeId = Integer.parseInt(request.getParameter("noticeId"));
			notice.setNoticeId(noticeId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			notice.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			notice.setContent(content);
			String publishDate = new String(request.getParameter("publishDate").getBytes("iso-8859-1"), "UTF-8");
			notice.setPublishDate(publishDate);

			/* 调用业务层执行更新操作 */
			String result = noticeDAO.UpdateNotice(notice);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
