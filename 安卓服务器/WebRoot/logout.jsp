<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
	session.removeAttribute("username");	//移除保存在session中的username属性
	session.invalidate();
	out.println("<script>top.location='" + basePath +"login/login_view.action';</script>");
%>