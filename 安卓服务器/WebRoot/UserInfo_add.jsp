<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>����û�</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var user_name = document.getElementById("userInfo.user_name").value;
    if(user_name=="") {
        alert('�������û���!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var userType = document.getElementById("userInfo.userType").value;
    if(userType=="") {
        alert('�������û�����!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var gender = document.getElementById("userInfo.gender").value;
    if(gender=="") {
        alert('�������Ա�!');
        return false;
    }
    var telephone = document.getElementById("userInfo.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var email = document.getElementById("userInfo.email").value;
    if(email=="") {
        alert('����������!');
        return false;
    }
    var shenHeState = document.getElementById("userInfo.shenHeState").value;
    if(shenHeState=="") {
        alert('���������״̬!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="UserInfo/UserInfo_AddUserInfo.action" method="post" id="userInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�û���:</td>
    <td width=70%><input id="userInfo.user_name" name="userInfo.user_name" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>�û�����:</td>
    <td width=70%><input id="userInfo.userType" name="userInfo.userType" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="userInfo.gender" name="userInfo.gender" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="userInfo.birthDate"  name="userInfo.birthDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>�û���Ƭ:</td>
    <td width=70%><input id="userPhotoFile" name="userPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="userInfo.email" name="userInfo.email" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>��֤�ļ�:</td>
    <td width=70%><input id="authFileFile" name="authFileFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><input id="userInfo.shenHeState" name="userInfo.shenHeState" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ע��ʱ��:</td>
    <td width=70%><input id="userInfo.regTime" name="userInfo.regTime" type="text" size="30" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
