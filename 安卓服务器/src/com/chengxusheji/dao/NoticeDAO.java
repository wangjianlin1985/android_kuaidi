package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Notice;

@Service @Transactional
public class NoticeDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddNotice(Notice notice) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(notice);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryNoticeInfo(String title,String publishDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Notice notice where 1=1";
    	if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List noticeList = q.list();
    	return (ArrayList<Notice>) noticeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryNoticeInfo(String title,String publishDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Notice notice where 1=1";
    	if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	List noticeList = q.list();
    	return (ArrayList<Notice>) noticeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Notice> QueryAllNoticeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Notice";
        Query q = s.createQuery(hql);
        List noticeList = q.list();
        return (ArrayList<Notice>) noticeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,String publishDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Notice notice where 1=1";
        if(!title.equals("")) hql = hql + " and notice.title like '%" + title + "%'";
        if(!publishDate.equals("")) hql = hql + " and notice.publishDate like '%" + publishDate + "%'";
        Query q = s.createQuery(hql);
        List noticeList = q.list();
        recordNumber = noticeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Notice GetNoticeByNoticeId(int noticeId) {
        Session s = factory.getCurrentSession();
        Notice notice = (Notice)s.get(Notice.class, noticeId);
        return notice;
    }

    /*更新Notice信息*/
    public void UpdateNotice(Notice notice) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(notice);
    }

    /*删除Notice信息*/
    public void DeleteNotice (int noticeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object notice = s.load(Notice.class, noticeId);
        s.delete(notice);
    }

}
