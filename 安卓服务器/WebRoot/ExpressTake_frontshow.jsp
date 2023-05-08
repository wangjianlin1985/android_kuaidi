<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ExpressTake" %>
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
    ExpressTake expressTake = (ExpressTake)request.getAttribute("expressTake");

%>
<HTML><HEAD><TITLE>�鿴��ݴ���</TITLE>
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
    <td width=70%><%=expressTake.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=expressTake.getTaskTitle() %></td>
  </tr>

  <tr>
    <td width=30%>������˾:</td>
    <td width=70%>
      <%=expressTake.getCompanyObj().getCompanyName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�˵�����:</td>
    <td width=70%><%=expressTake.getWaybill() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ���:</td>
    <td width=70%><%=expressTake.getReceiverName() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ��绰:</td>
    <td width=70%><%=expressTake.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>�ջ���ע:</td>
    <td width=70%><%=expressTake.getReceiveMemo() %></td>
  </tr>

  <tr>
    <td width=30%>�ʹ��ַ:</td>
    <td width=70%><%=expressTake.getTakePlace() %></td>
  </tr>

  <tr>
    <td width=30%>���ñ���:</td>
    <td width=70%><%=expressTake.getGiveMoney() %></td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%><%=expressTake.getTakeStateObj() %></td>
  </tr>

  <tr>
    <td width=30%>���񷢲���:</td>
    <td width=70%>
      <%=expressTake.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=expressTake.getAddTime() %></td>
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
