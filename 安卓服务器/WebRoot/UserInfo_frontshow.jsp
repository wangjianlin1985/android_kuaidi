<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

%>
<HTML><HEAD><TITLE>�鿴�û�</TITLE>
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
    <td width=30%>�û���:</td>
    <td width=70%><%=userInfo.getUser_name() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=userInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>�û�����:</td>
    <td width=70%><%=userInfo.getUserType() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=userInfo.getName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=userInfo.getGender() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat birthDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthDateSDF.format(userInfo.getBirthDate()) %></td>
  </tr>

  <tr>
    <td width=30%>�û���Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=userInfo.getUserPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=userInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=userInfo.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><%=userInfo.getAddress() %></td>
  </tr>

  <tr>
    <td width=30%>��֤�ļ�:</td>
    <td width=70%>
    	<% if(userInfo.getAuthFile()==null || userInfo.getAuthFile().equals("")) { %>
    	�����ļ�
    	<% } else { %>
    	<a href="<%=basePath %><%=userInfo.getAuthFile() %>" target="_blank"><%=userInfo.getAuthFile() %></a><br/>
    	<% } %>
    </td>
  </tr>
  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><%=userInfo.getShenHeState() %></td>
  </tr>

  <tr>
    <td width=30%>ע��ʱ��:</td>
    <td width=70%><%=userInfo.getRegTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
