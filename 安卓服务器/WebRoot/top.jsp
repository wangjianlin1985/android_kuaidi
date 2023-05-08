<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}
.STYLE2 {font-size: 9px}
.STYLE3 {
	color: #033d61;
	font-size: 12px;
}
-->
</style>

<script language="JavaScript">
function tick() {
var hours, minutes, seconds;
var intHours, intMinutes, intSeconds;
var today;
today = new Date();
intYear = today.getFullYear();
intMonth = today.getMonth() + 1;
intDay = today.getDate();
intHours = today.getHours();
intMinutes = today.getMinutes();
intSeconds = today.getSeconds();

if (intHours == 0) {
hours = "00:";
} else if (intHours < 10) { 
hours = "0" + intHours+":";
} else {
hours = intHours + ":";
}

if (intMinutes < 10) {
minutes = "0"+intMinutes+":";
} else {
minutes = intMinutes+":";
}
if (intSeconds < 10) {
seconds = "0"+intSeconds+" ";
} else {
seconds = intSeconds+" ";
} 
timeString = intYear + "年" + intMonth + "月" + intDay + "日" + " " + hours+minutes+seconds;
Clock.innerHTML = timeString;
window.setTimeout("tick();", 1000);
}

window.onload = tick;
//-->

</script>


</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="70" background="images/main_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="24"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="270" height="24" background="images/main_03.gif">&nbsp;</td>
            <td width="505" background="images/main_04.gif">&nbsp;</td>
            <td>&nbsp;</td>
            <td width="21"><img src="images/main_07.gif" width="21" height="24"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="images/main_09.jpg" width="170" height="38"/></td>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="77%" height="25" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="50" height="19"><div align="center"><img src="images/main_12.gif" onclick="javascript:top.location.href='<%=basePath %>index.jsp';"  style="cursor:hand;" width="49" height="19"></div></td>
                    <td width="50"><div align="center"><img src="images/main_14.gif" onclick="javascript:self.parent.frames['mainFrame'].history.go(-1);" style="cursor:hand;"  width="48" height="19"></div></td>
                    <td width="50"><div align="center"><img src="images/main_16.gif" onclick="javascript:self.parent.frames['mainFrame'].history.go(1);" style="cursor:hand;" width="48" height="19"></div></td>
                    <td width="50"><div align="center"><img src="images/main_18.gif" onclick="javascript:self.parent.frames['mainFrame'].location.reload();" style="cursor:hand;"  width="48" height="19"></div></td>
                    <td width="50"><div align="center"><img onclick="javascript:location.href='logout.jsp';" style="cursor:hand;" src="images/main_20.gif" width="48" height="19"></div></td>
                    <td width="26"><div align="center"><img src="images/main_21.gif" width="26" height="19"></div></td>
                    <td width="100"><div align="center"><!--<img src="images/main_22.gif" width="98" height="19">--></div></td>
                    <td>&nbsp;</td>
                  </tr>
                </table></td>
                <td width="320" valign="bottom"  nowrap="nowrap"><div align="right"><span class="STYLE1"><span class="STYLE2">■</span> 欢迎[管理员]:<font color=blue><%=session.getAttribute("username") %></font>&nbsp;现在是：<span id="Clock"></span></span></div></td>
              </tr>
            </table></td>
            <td width="21"><img src="images/main_11.gif" width="21" height="38"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="8" style=" line-height:8px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="270" background="images/main_29.gif" style=" line-height:8px;">&nbsp;</td>
            <td width="505" background="images/main_30.gif" style=" line-height:8px;">&nbsp;</td>
            <td style=" line-height:8px;">&nbsp;</td>
            <td width="21" style=" line-height:8px;"><img src="images/main_31.gif" width="21" height="8"></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="28" background="images/main_36.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="177" height="28" background="images/main_32.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="20%"  height="22">&nbsp;</td>
            <td width="59%" valign="bottom"><div align="center" class="STYLE1">当前用户：<%=session.getAttribute("username") %></div></td>
            <td width="21%">&nbsp;</td>
          </tr>
        </table></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="65" height="28"><table width="100%" border="0" cellspacing="0" cellpadding="0">
         
            <td><marquee width="400">欢迎使用本系统！</marquee></td>
          </tr>
        </table></td>
        <td width="21"><img src="images/main_37.gif" width="21" height="28"></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
