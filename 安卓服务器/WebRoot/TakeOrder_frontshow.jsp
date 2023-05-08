<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.TakeOrder" %>
<%@ page import="com.chengxusheji.domain.ExpressTake" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.OrderState" %>
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

%>
<HTML><HEAD><TITLE>�鿴���ö���</TITLE>
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
    <td width=30%>����id:</td>
    <td width=70%><%=takeOrder.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>���õĿ��:</td>
    <td width=70%>
      <%=takeOrder.getExpressTakeObj().getTaskTitle() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <%=takeOrder.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ӵ�ʱ��:</td>
    <td width=70%><%=takeOrder.getTakeTime() %></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <%=takeOrder.getOrderStateObj().getOrderStateName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ʵʱ��̬:</td>
    <td width=70%><%=takeOrder.getSsdt() %></td>
  </tr>

  <tr>
    <td width=30%>�û�����:</td>
    <td width=70%><%=takeOrder.getEvaluate() %></td>
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
