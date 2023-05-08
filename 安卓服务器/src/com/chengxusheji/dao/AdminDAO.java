package com.chengxusheji.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Admin; 

@Service @Transactional
public class AdminDAO {

	@Resource  SessionFactory factory;

	/*����ҵ���߼�������Ϣ�ֶ�*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*��֤�û���¼*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean CheckLogin(Admin admin) { 
		Session s = factory.getCurrentSession();  
		Admin db_admin = (Admin)s.get(Admin.class, admin.getUsername());
		if(db_admin == null) { 
			this.errMessage = " �˺Ų����� ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_admin.getPassword().equals(admin.getPassword())) {
			this.errMessage = " ���벻��ȷ! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	

	/*�޸��û���¼����*/
	public void ChangePassword(String username, String newPassword) {
		Session s = factory.getCurrentSession(); 
		Admin db_admin = (Admin)s.get(Admin.class, username);
		db_admin.setPassword(newPassword);
		s.save(db_admin);
	}
	
	/*�����û�����ȡ����Ա����*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public  Admin GetAdmin(String username) {
		Session s = factory.getCurrentSession(); 
		Admin db_admin = null;
		
		db_admin = (Admin)s.get(Admin.class, username); 
		
		return db_admin;
	}
}
