<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

%>
<HTML><HEAD><TITLE>查看用户</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>用户名:</td>
    <td width=70%><%=userInfo.getUser_name() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=userInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>用户类型:</td>
    <td width=70%><%=userInfo.getUserType() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=userInfo.getName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=userInfo.getGender() %></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
        <% java.text.DateFormat birthDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthDateSDF.format(userInfo.getBirthDate()) %></td>
  </tr>

  <tr>
    <td width=30%>用户照片:</td>
    <td width=70%><img src="<%=basePath %><%=userInfo.getUserPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=userInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>邮箱:</td>
    <td width=70%><%=userInfo.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><%=userInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>认证文件:</td>
    <td width=70%>
    	<% if(userInfo.getAuthFile()==null || userInfo.getAuthFile().equals("")) { %>
    	暂无文件
    	<% } else { %>
    	<a href="<%=basePath %><%=userInfo.getAuthFile() %>" target="_blank"><%=userInfo.getAuthFile() %></a><br/>
    	<% } %>
    </td>
  </tr>
  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%><%=userInfo.getShenHeState() %></td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><%=userInfo.getRegTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
