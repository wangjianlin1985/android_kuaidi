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

@Service @Transactional
public class CompanyDAO {

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
    public void AddCompany(Company company) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(company);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Company> QueryCompanyInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Company company where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List companyList = q.list();
    	return (ArrayList<Company>) companyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Company> QueryCompanyInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Company company where 1=1";
    	Query q = s.createQuery(hql);
    	List companyList = q.list();
    	return (ArrayList<Company>) companyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Company> QueryAllCompanyInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Company";
        Query q = s.createQuery(hql);
        List companyList = q.list();
        return (ArrayList<Company>) companyList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From Company company where 1=1";
        Query q = s.createQuery(hql);
        List companyList = q.list();
        recordNumber = companyList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Company GetCompanyByCompanyId(int companyId) {
        Session s = factory.getCurrentSession();
        Company company = (Company)s.get(Company.class, companyId);
        return company;
    }

    /*更新Company信息*/
    public void UpdateCompany(Company company) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(company);
    }

    /*删除Company信息*/
    public void DeleteCompany (int companyId) throws Exception {
        Session s = factory.getCurrentSession();
        Object company = s.load(Company.class, companyId);
        s.delete(company);
    }

}
