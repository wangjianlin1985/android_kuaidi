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
import com.chengxusheji.domain.UserInfo;

@Service @Transactional
public class UserInfoDAO {

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
    public void AddUserInfo(UserInfo userInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(userInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserInfo> QueryUserInfoInfo(String user_name,String userType,String name,String birthDate,String telephone,String shenHeState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From UserInfo userInfo where 1=1";
    	if(!user_name.equals("")) hql = hql + " and userInfo.user_name like '%" + user_name + "%'";
    	if(!userType.equals("")) hql = hql + " and userInfo.userType like '%" + userType + "%'";
    	if(!name.equals("")) hql = hql + " and userInfo.name like '%" + name + "%'";
    	if(!birthDate.equals("")) hql = hql + " and userInfo.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) hql = hql + " and userInfo.telephone like '%" + telephone + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and userInfo.shenHeState like '%" + shenHeState + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List userInfoList = q.list();
    	return (ArrayList<UserInfo>) userInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserInfo> QueryUserInfoInfo(String user_name,String userType,String name,String birthDate,String telephone,String shenHeState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From UserInfo userInfo where 1=1";
    	if(!user_name.equals("")) hql = hql + " and userInfo.user_name like '%" + user_name + "%'";
    	if(!userType.equals("")) hql = hql + " and userInfo.userType like '%" + userType + "%'";
    	if(!name.equals("")) hql = hql + " and userInfo.name like '%" + name + "%'";
    	if(!birthDate.equals("")) hql = hql + " and userInfo.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) hql = hql + " and userInfo.telephone like '%" + telephone + "%'";
    	if(!shenHeState.equals("")) hql = hql + " and userInfo.shenHeState like '%" + shenHeState + "%'";
    	Query q = s.createQuery(hql);
    	List userInfoList = q.list();
    	return (ArrayList<UserInfo>) userInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserInfo> QueryAllUserInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From UserInfo";
        Query q = s.createQuery(hql);
        List userInfoList = q.list();
        return (ArrayList<UserInfo>) userInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String user_name,String userType,String name,String birthDate,String telephone,String shenHeState) {
        Session s = factory.getCurrentSession();
        String hql = "From UserInfo userInfo where 1=1";
        if(!user_name.equals("")) hql = hql + " and userInfo.user_name like '%" + user_name + "%'";
        if(!userType.equals("")) hql = hql + " and userInfo.userType like '%" + userType + "%'";
        if(!name.equals("")) hql = hql + " and userInfo.name like '%" + name + "%'";
        if(!birthDate.equals("")) hql = hql + " and userInfo.birthDate like '%" + birthDate + "%'";
        if(!telephone.equals("")) hql = hql + " and userInfo.telephone like '%" + telephone + "%'";
        if(!shenHeState.equals("")) hql = hql + " and userInfo.shenHeState like '%" + shenHeState + "%'";
        Query q = s.createQuery(hql);
        List userInfoList = q.list();
        recordNumber = userInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public UserInfo GetUserInfoByUser_name(String user_name) {
        Session s = factory.getCurrentSession();
        UserInfo userInfo = (UserInfo)s.get(UserInfo.class, user_name);
        return userInfo;
    }

    /*更新UserInfo信息*/
    public void UpdateUserInfo(UserInfo userInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(userInfo);
    }

    /*删除UserInfo信息*/
    public void DeleteUserInfo (String user_name) throws Exception {
        Session s = factory.getCurrentSession();
        Object userInfo = s.load(UserInfo.class, user_name);
        s.delete(userInfo);
    }

}
