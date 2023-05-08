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
import com.chengxusheji.domain.ExpressTake;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.OrderState;
import com.chengxusheji.domain.TakeOrder;

@Service @Transactional
public class TakeOrderDAO {

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
    public void AddTakeOrder(TakeOrder takeOrder) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(takeOrder);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TakeOrder> QueryTakeOrderInfo(ExpressTake expressTakeObj,UserInfo userObj,String takeTime,OrderState orderStateObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TakeOrder takeOrder where 1=1";
    	if(null != expressTakeObj && expressTakeObj.getOrderId()!=0) hql += " and takeOrder.expressTakeObj.orderId=" + expressTakeObj.getOrderId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and takeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!takeTime.equals("")) hql = hql + " and takeOrder.takeTime like '%" + takeTime + "%'";
    	if(null != orderStateObj && orderStateObj.getOrderStateId()!=0) hql += " and takeOrder.orderStateObj.orderStateId=" + orderStateObj.getOrderStateId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List takeOrderList = q.list();
    	return (ArrayList<TakeOrder>) takeOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TakeOrder> QueryTakeOrderInfo(ExpressTake expressTakeObj,UserInfo userObj,String takeTime,OrderState orderStateObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From TakeOrder takeOrder where 1=1";
    	if(null != expressTakeObj && expressTakeObj.getOrderId()!=0) hql += " and takeOrder.expressTakeObj.orderId=" + expressTakeObj.getOrderId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and takeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!takeTime.equals("")) hql = hql + " and takeOrder.takeTime like '%" + takeTime + "%'";
    	if(null != orderStateObj && orderStateObj.getOrderStateId()!=0) hql += " and takeOrder.orderStateObj.orderStateId=" + orderStateObj.getOrderStateId();
    	Query q = s.createQuery(hql);
    	List takeOrderList = q.list();
    	return (ArrayList<TakeOrder>) takeOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<TakeOrder> QueryAllTakeOrderInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From TakeOrder";
        Query q = s.createQuery(hql);
        List takeOrderList = q.list();
        return (ArrayList<TakeOrder>) takeOrderList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(ExpressTake expressTakeObj,UserInfo userObj,String takeTime,OrderState orderStateObj) {
        Session s = factory.getCurrentSession();
        String hql = "From TakeOrder takeOrder where 1=1";
        if(null != expressTakeObj && expressTakeObj.getOrderId()!=0) hql += " and takeOrder.expressTakeObj.orderId=" + expressTakeObj.getOrderId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and takeOrder.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!takeTime.equals("")) hql = hql + " and takeOrder.takeTime like '%" + takeTime + "%'";
        if(null != orderStateObj && orderStateObj.getOrderStateId()!=0) hql += " and takeOrder.orderStateObj.orderStateId=" + orderStateObj.getOrderStateId();
        Query q = s.createQuery(hql);
        List takeOrderList = q.list();
        recordNumber = takeOrderList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public TakeOrder GetTakeOrderByOrderId(int orderId) {
        Session s = factory.getCurrentSession();
        TakeOrder takeOrder = (TakeOrder)s.get(TakeOrder.class, orderId);
        return takeOrder;
    }

    /*����TakeOrder��Ϣ*/
    public void UpdateTakeOrder(TakeOrder takeOrder) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(takeOrder);
    }

    /*ɾ��TakeOrder��Ϣ*/
    public void DeleteTakeOrder (int orderId) throws Exception {
        Session s = factory.getCurrentSession();
        Object takeOrder = s.load(TakeOrder.class, orderId);
        s.delete(takeOrder);
    }

}
