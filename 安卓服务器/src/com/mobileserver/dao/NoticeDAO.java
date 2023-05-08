package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Notice;
import com.mobileserver.util.DB;

public class NoticeDAO {

	public List<Notice> QueryNotice(String title,String publishDate) {
		List<Notice> noticeList = new ArrayList<Notice>();
		DB db = new DB();
		String sql = "select * from Notice where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!publishDate.equals(""))
			sql += " and publishDate like '%" + publishDate + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Notice notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setPublishDate(rs.getString("publishDate"));
				noticeList.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return noticeList;
	}
	/* 传入新闻公告对象，进行新闻公告的添加业务 */
	public String AddNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新新闻公告 */
			String sqlString = "insert into Notice(title,content,publishDate) values (";
			sqlString += "'" + notice.getTitle() + "',";
			sqlString += "'" + notice.getContent() + "',";
			sqlString += "'" + notice.getPublishDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "新闻公告添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除新闻公告 */
	public String DeleteNotice(int noticeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Notice where noticeId=" + noticeId;
			db.executeUpdate(sqlString);
			result = "新闻公告删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据公告id获取到新闻公告 */
	public Notice GetNotice(int noticeId) {
		Notice notice = null;
		DB db = new DB();
		String sql = "select * from Notice where noticeId=" + noticeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				notice = new Notice();
				notice.setNoticeId(rs.getInt("noticeId"));
				notice.setTitle(rs.getString("title"));
				notice.setContent(rs.getString("content"));
				notice.setPublishDate(rs.getString("publishDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return notice;
	}
	/* 更新新闻公告 */
	public String UpdateNotice(Notice notice) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Notice set ";
			sql += "title='" + notice.getTitle() + "',";
			sql += "content='" + notice.getContent() + "',";
			sql += "publishDate='" + notice.getPublishDate() + "'";
			sql += " where noticeId=" + notice.getNoticeId();
			db.executeUpdate(sql);
			result = "新闻公告更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "新闻公告更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
