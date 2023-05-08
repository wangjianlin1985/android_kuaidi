<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ExpressTake" %>
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
    ExpressTake expressTake = (ExpressTake)request.getAttribute("expressTake");

%>
<HTML><HEAD><TITLE>查看快递代拿</TITLE>
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
    <td width=30%>订单id:</td>
    <td width=70%><%=expressTake.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>代拿任务:</td>
    <td width=70%><%=expressTake.getTaskTitle() %></td>
  </tr>

  <tr>
    <td width=30%>物流公司:</td>
    <td width=70%>
      <%=expressTake.getCompanyObj().getCompanyName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>运单号码:</td>
    <td width=70%><%=expressTake.getWaybill() %></td>
  </tr>

  <tr>
    <td width=30%>收货人:</td>
    <td width=70%><%=expressTake.getReceiverName() %></td>
  </tr>

  <tr>
    <td width=30%>收货电话:</td>
    <td width=70%><%=expressTake.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>收货备注:</td>
    <td width=70%><%=expressTake.getReceiveMemo() %></td>
  </tr>

  <tr>
    <td width=30%>送达地址:</td>
    <td width=70%><%=expressTake.getTakePlace() %></td>
  </tr>

  <tr>
    <td width=30%>代拿报酬:</td>
    <td width=70%><%=expressTake.getGiveMoney() %></td>
  </tr>

  <tr>
    <td width=30%>代拿状态:</td>
    <td width=70%><%=expressTake.getTakeStateObj() %></td>
  </tr>

  <tr>
    <td width=30%>任务发布人:</td>
    <td width=70%>
      <%=expressTake.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>发布时间:</td>
    <td width=70%><%=expressTake.getAddTime() %></td>
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
