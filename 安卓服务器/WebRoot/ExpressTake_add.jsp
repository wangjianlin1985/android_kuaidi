<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Company��Ϣ
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӿ�ݴ���</TITLE> 
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
    var taskTitle = document.getElementById("expressTake.taskTitle").value;
    if(taskTitle=="") {
        alert('�������������!');
        return false;
    }
    var waybill = document.getElementById("expressTake.waybill").value;
    if(waybill=="") {
        alert('�������˵�����!');
        return false;
    }
    var receiverName = document.getElementById("expressTake.receiverName").value;
    if(receiverName=="") {
        alert('�������ջ���!');
        return false;
    }
    var telephone = document.getElementById("expressTake.telephone").value;
    if(telephone=="") {
        alert('�������ջ��绰!');
        return false;
    }
    var receiveMemo = document.getElementById("expressTake.receiveMemo").value;
    if(receiveMemo=="") {
        alert('�������ջ���ע!');
        return false;
    }
    var takePlace = document.getElementById("expressTake.takePlace").value;
    if(takePlace=="") {
        alert('�������ʹ��ַ!');
        return false;
    }
    var takeStateObj = document.getElementById("expressTake.takeStateObj").value;
    if(takeStateObj=="") {
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
    <s:form action="ExpressTake/ExpressTake_AddExpressTake.action" method="post" id="expressTakeAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="expressTake.taskTitle" name="expressTake.taskTitle" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>������˾:</td>
    <td width=70%>
      <select name="expressTake.companyObj.companyId">
      <%
        for(Company company:companyList) {
      %>
          <option value='<%=company.getCompanyId() %>'><%=company.getCompanyName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�˵�����:</td>
    <td width=70%><input id="expressTake.waybill" name="expressTake.waybill" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�ջ���:</td>
    <td width=70%><input id="expressTake.receiverName" name="expressTake.receiverName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�ջ��绰:</td>
    <td width=70%><input id="expressTake.telephone" name="expressTake.telephone" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>�ջ���ע:</td>
    <td width=70%><textarea id="expressTake.receiveMemo" name="expressTake.receiveMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>�ʹ��ַ:</td>
    <td width=70%><input id="expressTake.takePlace" name="expressTake.takePlace" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>���ñ���:</td>
    <td width=70%><input id="expressTake.giveMoney" name="expressTake.giveMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%><input id="expressTake.takeStateObj" name="expressTake.takeStateObj" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>���񷢲���:</td>
    <td width=70%>
      <select name="expressTake.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="expressTake.addTime" name="expressTake.addTime" type="text" size="20" /></td>
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
