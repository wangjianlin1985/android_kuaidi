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
<HTML><HEAD><TITLE>添加用户</TITLE> 
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
/*验证表单*/
function checkForm() {
    var user_name = document.getElementById("userInfo.user_name").value;
    if(user_name=="") {
        alert('请输入用户名!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var userType = document.getElementById("userInfo.userType").value;
    if(userType=="") {
        alert('请输入用户类型!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('请输入姓名!');
        return false;
    }
    var gender = document.getElementById("userInfo.gender").value;
    if(gender=="") {
        alert('请输入性别!');
        return false;
    }
    var telephone = document.getElementById("userInfo.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var email = document.getElementById("userInfo.email").value;
    if(email=="") {
        alert('请输入邮箱!');
        return false;
    }
    var shenHeState = document.getElementById("userInfo.shenHeState").value;
    if(shenHeState=="") {
        alert('请输入审核状态!');
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
    <td width=30%>用户名:</td>
    <td width=70%><input id="userInfo.user_name" name="userInfo.user_name" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>用户类型:</td>
    <td width=70%><input id="userInfo.userType" name="userInfo.userType" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="userInfo.gender" name="userInfo.gender" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
    <td width=70%><input type="text" readonly id="userInfo.birthDate"  name="userInfo.birthDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>用户照片:</td>
    <td width=70%><input id="userPhotoFile" name="userPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>邮箱:</td>
    <td width=70%><input id="userInfo.email" name="userInfo.email" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>认证文件:</td>
    <td width=70%><input id="authFileFile" name="authFileFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%><input id="userInfo.shenHeState" name="userInfo.shenHeState" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>注册时间:</td>
    <td width=70%><input id="userInfo.regTime" name="userInfo.regTime" type="text" size="30" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
