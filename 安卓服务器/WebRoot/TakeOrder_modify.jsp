<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.TakeOrder" %>
<%@ page import="com.chengxusheji.domain.ExpressTake" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�ExpressTake��Ϣ
    List<ExpressTake> expressTakeList = (List<ExpressTake>)request.getAttribute("expressTakeList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�OrderState��Ϣ
    List<OrderState> orderStateList = (List<OrderState>)request.getAttribute("orderStateList");
    TakeOrder takeOrder = (TakeOrder)request.getAttribute("takeOrder");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ĵ��ö���</TITLE>
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
    var takeTime = document.getElementById("takeOrder.takeTime").value;
    if(takeTime=="") {
        alert('������ӵ�ʱ��!');
        return false;
    }
    var ssdt = document.getElementById("takeOrder.ssdt").value;
    if(ssdt=="") {
        alert('������ʵʱ��̬!');
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
    <TD align="left" vAlign=top ><s:form action="TakeOrder/TakeOrder_ModifyTakeOrder.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="takeOrder.orderId" name="takeOrder.orderId" type="text" value="<%=takeOrder.getOrderId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���õĿ��:</td>
    <td width=70%>
      <select name="takeOrder.expressTakeObj.orderId">
      <%
        for(ExpressTake expressTake:expressTakeList) {
          String selected = "";
          if(expressTake.getOrderId() == takeOrder.getExpressTakeObj().getOrderId())
            selected = "selected";
      %>
          <option value='<%=expressTake.getOrderId() %>' <%=selected %>><%=expressTake.getTaskTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="takeOrder.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(takeOrder.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ӵ�ʱ��:</td>
    <td width=70%><input id="takeOrder.takeTime" name="takeOrder.takeTime" type="text" size="20" value='<%=takeOrder.getTakeTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <select name="takeOrder.orderStateObj.orderStateId">
      <%
        for(OrderState orderState:orderStateList) {
          String selected = "";
          if(orderState.getOrderStateId() == takeOrder.getOrderStateObj().getOrderStateId())
            selected = "selected";
      %>
          <option value='<%=orderState.getOrderStateId() %>' <%=selected %>><%=orderState.getOrderStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ʵʱ��̬:</td>
    <td width=70%><textarea id="takeOrder.ssdt" name="takeOrder.ssdt" rows=5 cols=50><%=takeOrder.getSsdt() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�û�����:</td>
    <td width=70%><input id="takeOrder.evaluate" name="takeOrder.evaluate" type="text" size="60" value='<%=takeOrder.getEvaluate() %>'/></td>
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
