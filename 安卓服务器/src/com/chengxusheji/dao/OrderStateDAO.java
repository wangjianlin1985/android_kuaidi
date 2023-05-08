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
import com.chengxusheji.domain.OrderState;

@Service @Transactional
public class OrderStateDAO {

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
    public void AddOrderState(OrderState orderState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(orderState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderState> QueryOrderStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderState orderState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List orderStateList = q.list();
    	return (ArrayList<OrderState>) orderStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderState> QueryOrderStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From OrderState orderState where 1=1";
    	Query q = s.createQuery(hql);
    	List orderStateList = q.list();
    	return (ArrayList<OrderState>) orderStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<OrderState> QueryAllOrderStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From OrderState";
        Query q = s.createQuery(hql);
        List orderStateList = q.list();
        return (ArrayList<OrderState>) orderStateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From OrderState orderState where 1=1";
        Query q = s.createQuery(hql);
        List orderStateList = q.list();
        recordNumber = orderStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public OrderState GetOrderStateByOrderStateId(int orderStateId) {
        Session s = factory.getCurrentSession();
        OrderState orderState = (OrderState)s.get(OrderState.class, orderStateId);
        return orderState;
    }

    /*更新OrderState信息*/
    public void UpdateOrderState(OrderState orderState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(orderState);
    }

    /*删除OrderState信息*/
    public void DeleteOrderState (int orderStateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object orderState = s.load(OrderState.class, orderStateId);
        s.delete(orderState);
    }

}
