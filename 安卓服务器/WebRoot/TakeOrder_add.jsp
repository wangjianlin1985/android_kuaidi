<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.ExpressTake" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ExpressTake信息
    List<ExpressTake> expressTakeList = (List<ExpressTake>)request.getAttribute("expressTakeList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的OrderState信息
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加代拿订单</TITLE> 
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
    var takeTime = document.getElementById("takeOrder.takeTime").value;
    if(takeTime=="") {
        alert('请输入接单时间!');
        return false;
    }
    var ssdt = document.getElementById("takeOrder.ssdt").value;
    if(ssdt=="") {
        alert('请输入实时动态!');
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
    <s:form action="TakeOrder/TakeOrder_AddTakeOrder.action" method="post" id="takeOrderAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>代拿的快递:</td>
    <td width=70%>
      <select name="takeOrder.expressTakeObj.orderId">
      <%
        for(ExpressTake expressTake:expressTakeList) {
      %>
          <option value='<%=expressTake.getOrderId() %>'><%=expressTake.getTaskTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>接任务人:</td>
    <td width=70%>
      <select name="takeOrder.userObj.user_name">
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
    <td width=30%>接单时间:</td>
    <td width=70%><input id="takeOrder.takeTime" name="takeOrder.takeTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>订单状态:</td>
    <td width=70%>
      <select name="takeOrder.orderStateObj.orderStateId">
      <%
        for(OrderState orderState:orderStateList) {
      %>
          <option value='<%=orderState.getOrderStateId() %>'><%=orderState.getOrderStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>实时动态:</td>
    <td width=70%><textarea id="takeOrder.ssdt" name="takeOrder.ssdt" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>用户评价:</td>
    <td width=70%><input id="takeOrder.evaluate" name="takeOrder.evaluate" type="text" size="60" /></td>
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
