<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
	session.removeAttribute("username");	//�Ƴ�������session�е�username����
	session.invalidate();
	out.println("<script>top.location='" + basePath +"login/login_view.action';</script>");
%>