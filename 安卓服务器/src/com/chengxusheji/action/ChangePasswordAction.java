package com.chengxusheji.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.AdminDAO;
import com.chengxusheji.domain.Admin;

@Controller @Scope("prototype")
public class ChangePasswordAction {
	
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
	
	@Resource AdminDAO adminDAO; 

	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getNewPassword2() {
		return newPassword2;
	}


	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}


	/*�޸�����*/
	public String execute() {
		ActionContext ctx = ActionContext.getContext(); 
		if(oldPassword.equals("")) {
			ctx.put("error",  java.net.URLEncoder.encode("�������������!"));
			return "error";
		}
		if(newPassword.equals("")) {
			ctx.put("error",  java.net.URLEncoder.encode("�������������!"));
			return "error";
		}
		if(!newPassword2.equals(newPassword)) {
			ctx.put("error",  java.net.URLEncoder.encode("�����������벻һ��!"));
			return "error";
		}
		String username = (String)ctx.getSession().get("username"); 
		Admin admin = adminDAO.GetAdmin(username);
		if(!admin.getPassword().equals(oldPassword)) {
			ctx.put("error",  java.net.URLEncoder.encode("�����벻��ȷ!"));
			return "error";
		}
		
		try { 
			adminDAO.ChangePassword(username,newPassword);
			ctx.put("message",  java.net.URLEncoder.encode("�����޸ĳɹ�!"));
			return "change_success";
		} catch (Exception e) {
			e.printStackTrace();
			ctx.put("error",  java.net.URLEncoder.encode("�������ʧ��!"));
			return "error";
		}  
		
	}

}
