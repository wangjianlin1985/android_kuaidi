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

	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean CheckLogin(Admin admin) { 
		Session s = factory.getCurrentSession();  
		Admin db_admin = (Admin)s.get(Admin.class, admin.getUsername());
		if(db_admin == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_admin.getPassword().equals(admin.getPassword())) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
	

	/*修改用户登录密码*/
	public void ChangePassword(String username, String newPassword) {
		Session s = factory.getCurrentSession(); 
		Admin db_admin = (Admin)s.get(Admin.class, username);
		db_admin.setPassword(newPassword);
		s.save(db_admin);
	}
	
	/*根据用户名获取管理员对象*/
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public  Admin GetAdmin(String username) {
		Session s = factory.getCurrentSession(); 
		Admin db_admin = null;
		
		db_admin = (Admin)s.get(Admin.class, username); 
		
		return db_admin;
	}
}
