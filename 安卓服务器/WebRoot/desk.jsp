<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>信息管理系统 - 桌面</title>
<link href="<%=basePath %>css/desk.css" rel="stylesheet" type="text/css"> 
</head>

<body>
<table align=center width="90%" border="0" cellspacing="0" cellpadding="0" height="100%">
      <tr>
        <td valign="top"><font color="#336666">
         <font color='red'><br/><br/>
        
          欢迎使用本系统

			系统开发环境: MyEclipse8.5 + Tomcat6.0 + mysql5.0 <br/><br/>
			系统采用技术: struts 2 + Spring + Hibernate (SSH2)   <br/><br/> 
		 
					</font><font color=blue>本系统开发时间: 2019年</font>
				</td>
      </tr>
    </table>
</body>
</html>

