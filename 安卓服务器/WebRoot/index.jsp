<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>224����Androidƽ̨�Ŀ�ݴ���APP����-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û�</a></li> 
			<li><a href="<%=basePath %>ExpressTake/ExpressTake_FrontQueryExpressTake.action" target="OfficeMain">��ݴ���</a></li> 
			<li><a href="<%=basePath %>Company/Company_FrontQueryCompany.action" target="OfficeMain">������˾</a></li> 
			<li><a href="<%=basePath %>OrderState/OrderState_FrontQueryOrderState.action" target="OfficeMain">����״̬</a></li> 
			<li><a href="<%=basePath %>TakeOrder/TakeOrder_FrontQueryTakeOrder.action" target="OfficeMain">���ö���</a></li> 
			<li><a href="<%=basePath %>Notice/Notice_FrontQueryNotice.action" target="OfficeMain">���Ź���</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
