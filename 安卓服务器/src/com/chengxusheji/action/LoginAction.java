package com.chengxusheji.action;

 
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.chengxusheji.dao.AdminDAO;
import com.chengxusheji.domain.Admin;

@Controller @Scope("prototype")
public class LoginAction extends ActionSupport {
 
	@Resource AdminDAO adminDAO;
	
	private Admin admin;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/*直接跳转到登陆界面*/
	public String view() {
		
		return "login_view";
	}
	 
	
	/* 验证用户登录 */
	public String CheckLogin() { 
		ActionContext ctx = ActionContext.getContext();
		if (!adminDAO.CheckLogin(admin)) {
			ctx.put("error",  java.net.URLEncoder.encode(adminDAO.getErrMessage()));
			return "error";
		}
		ctx.getSession().put("username", admin.getUsername());
		return "main_view";

		/*
		 * ActionContext ctx = ActionContext.getContext();
		 * ctx.getApplication().put("app", "应用范围");//往ServletContext里放入app
		 * ctx.getSession().put("ses", "session范围");//往session里放入ses ctx.put("req",
		 * "request范围");//往request里放入req ctx.put("names", Arrays.asList("老张", "老黎",
		 * "老方")); HttpServletRequest request = ServletActionContext.getRequest();
		 * ServletContext servletContext = ServletActionContext.getServletContext();
		 * request.setAttribute("req", "请求范围属性");
		 * request.getSession().setAttribute("ses", "会话范围属性");
		 * servletContext.setAttribute("app", "应用范围属性"); // HttpServletResponse
		 * response = ServletActionContext.getResponse();
		 */
	}

	

}
