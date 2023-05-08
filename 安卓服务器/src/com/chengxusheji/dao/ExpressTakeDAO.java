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
import com.chengxusheji.domain.Company;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.ExpressTake;

@Service @Transactional
public class ExpressTakeDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddExpressTake(ExpressTake expressTake) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(expressTake);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ExpressTake> QueryExpressTakeInfo(String taskTitle,Company companyObj,String waybill,String receiverName,String telephone,String takePlace,String takeStateObj,UserInfo userObj,String addTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ExpressTake expressTake where 1=1";
    	if(!taskTitle.equals("")) hql = hql + " and expressTake.taskTitle like '%" + taskTitle + "%'";
    	if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and expressTake.companyObj.companyId=" + companyObj.getCompanyId();
    	if(!waybill.equals("")) hql = hql + " and expressTake.waybill like '%" + waybill + "%'";
    	if(!receiverName.equals("")) hql = hql + " and expressTake.receiverName like '%" + receiverName + "%'";
    	if(!telephone.equals("")) hql = hql + " and expressTake.telephone like '%" + telephone + "%'";
    	if(!takePlace.equals("")) hql = hql + " and expressTake.takePlace like '%" + takePlace + "%'";
    	if(!takeStateObj.equals("")) hql = hql + " and expressTake.takeStateObj like '%" + takeStateObj + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and expressTake.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and expressTake.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List expressTakeList = q.list();
    	return (ArrayList<ExpressTake>) expressTakeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ExpressTake> QueryExpressTakeInfo(String taskTitle,Company companyObj,String waybill,String receiverName,String telephone,String takePlace,String takeStateObj,UserInfo userObj,String addTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ExpressTake expressTake where 1=1";
    	if(!taskTitle.equals("")) hql = hql + " and expressTake.taskTitle like '%" + taskTitle + "%'";
    	if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and expressTake.companyObj.companyId=" + companyObj.getCompanyId();
    	if(!waybill.equals("")) hql = hql + " and expressTake.waybill like '%" + waybill + "%'";
    	if(!receiverName.equals("")) hql = hql + " and expressTake.receiverName like '%" + receiverName + "%'";
    	if(!telephone.equals("")) hql = hql + " and expressTake.telephone like '%" + telephone + "%'";
    	if(!takePlace.equals("")) hql = hql + " and expressTake.takePlace like '%" + takePlace + "%'";
    	if(!takeStateObj.equals("")) hql = hql + " and expressTake.takeStateObj like '%" + takeStateObj + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and expressTake.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) hql = hql + " and expressTake.addTime like '%" + addTime + "%'";
    	Query q = s.createQuery(hql);
    	List expressTakeList = q.list();
    	return (ArrayList<ExpressTake>) expressTakeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ExpressTake> QueryAllExpressTakeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ExpressTake";
        Query q = s.createQuery(hql);
        List expressTakeList = q.list();
        return (ArrayList<ExpressTake>) expressTakeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String taskTitle,Company companyObj,String waybill,String receiverName,String telephone,String takePlace,String takeStateObj,UserInfo userObj,String addTime) {
        Session s = factory.getCurrentSession();
        String hql = "From ExpressTake expressTake where 1=1";
        if(!taskTitle.equals("")) hql = hql + " and expressTake.taskTitle like '%" + taskTitle + "%'";
        if(null != companyObj && companyObj.getCompanyId()!=0) hql += " and expressTake.companyObj.companyId=" + companyObj.getCompanyId();
        if(!waybill.equals("")) hql = hql + " and expressTake.waybill like '%" + waybill + "%'";
        if(!receiverName.equals("")) hql = hql + " and expressTake.receiverName like '%" + receiverName + "%'";
        if(!telephone.equals("")) hql = hql + " and expressTake.telephone like '%" + telephone + "%'";
        if(!takePlace.equals("")) hql = hql + " and expressTake.takePlace like '%" + takePlace + "%'";
        if(!takeStateObj.equals("")) hql = hql + " and expressTake.takeStateObj like '%" + takeStateObj + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and expressTake.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!addTime.equals("")) hql = hql + " and expressTake.addTime like '%" + addTime + "%'";
        Query q = s.createQuery(hql);
        List expressTakeList = q.list();
        recordNumber = expressTakeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ExpressTake GetExpressTakeByOrderId(int orderId) {
        Session s = factory.getCurrentSession();
        ExpressTake expressTake = (ExpressTake)s.get(ExpressTake.class, orderId);
        return expressTake;
    }

    /*����ExpressTake��Ϣ*/
    public void UpdateExpressTake(ExpressTake expressTake) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(expressTake);
    }

    /*ɾ��ExpressTake��Ϣ*/
    public void DeleteExpressTake (int orderId) throws Exception {
        Session s = factory.getCurrentSession();
        Object expressTake = s.load(ExpressTake.class, orderId);
        s.delete(expressTake);
    }

}
