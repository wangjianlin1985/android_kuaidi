<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Company" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Company信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加快递代拿</TITLE> 
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
    var taskTitle = document.getElementById("expressTake.taskTitle").value;
    if(taskTitle=="") {
        alert('请输入代拿任务!');
        return false;
    }
    var waybill = document.getElementById("expressTake.waybill").value;
    if(waybill=="") {
        alert('请输入运单号码!');
        return false;
    }
    var receiverName = document.getElementById("expressTake.receiverName").value;
    if(receiverName=="") {
        alert('请输入收货人!');
        return false;
    }
    var telephone = document.getElementById("expressTake.telephone").value;
    if(telephone=="") {
        alert('请输入收货电话!');
        return false;
    }
    var receiveMemo = document.getElementById("expressTake.receiveMemo").value;
    if(receiveMemo=="") {
        alert('请输入收货备注!');
        return false;
    }
    var takePlace = document.getElementById("expressTake.takePlace").value;
    if(takePlace=="") {
        alert('请输入送达地址!');
        return false;
    }
    var takeStateObj = document.getElementById("expressTake.takeStateObj").value;
    if(takeStateObj=="") {
        alert('请输入代拿状态!');
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
    <td width=30%>代拿任务:</td>
    <td width=70%><input id="expressTake.taskTitle" name="expressTake.taskTitle" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>物流公司:</td>
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
    <td width=30%>运单号码:</td>
    <td width=70%><input id="expressTake.waybill" name="expressTake.waybill" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>收货人:</td>
    <td width=70%><input id="expressTake.receiverName" name="expressTake.receiverName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>收货电话:</td>
    <td width=70%><input id="expressTake.telephone" name="expressTake.telephone" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>收货备注:</td>
    <td width=70%><textarea id="expressTake.receiveMemo" name="expressTake.receiveMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>送达地址:</td>
    <td width=70%><input id="expressTake.takePlace" name="expressTake.takePlace" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>代拿报酬:</td>
    <td width=70%><input id="expressTake.giveMoney" name="expressTake.giveMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>代拿状态:</td>
    <td width=70%><input id="expressTake.takeStateObj" name="expressTake.takeStateObj" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>任务发布人:</td>
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
    <td width=30%>发布时间:</td>
    <td width=70%><input id="expressTake.addTime" name="expressTake.addTime" type="text" size="20" /></td>
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
