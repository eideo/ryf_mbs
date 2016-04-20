<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>交易数据下载</title>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
int rand = new java.util.Random().nextInt(10000);
%>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="Expires" content="0"/>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../../public/css/head.css?<%=rand%>" rel="stylesheet" type="text/css"/> 
        <link href="../../public/css/wbox.css?<%=rand%>" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="../../dwr/engine.js?<%=rand%>"></script>
        <script type="text/javascript" src="../../dwr/util.js?<%=rand%>"></script>
        <script type="text/javascript" src='../../dwr/interface/CommonService.js?<%=rand%>'></script>
  		<script type="text/javascript" src='../../dwr/interface/FtpService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/merchant/mer_jsp_ftp.js?<%=rand%>"></script>
        <script type='text/javascript' src='../../public/datePicker/WdatePicker.js?<%=rand%>'></script>
    </head>
    <body onload="init();">
     <div class="style">
	<table class="tableBorder">
		<tbody>
			<tr>
				<td class="title" colspan="16">&nbsp;&nbsp; 交易数据下载
				</td>
			</tr>
			<tr>
				<td class="th1" bgcolor="#D9DFEE" align="right" width="11%">交易数据来源：</td>
                <td align="left" width="20%">
                <select style="width: 150px" id="ftp_source" name="ftp_source">
                    <option value="">全部...</option>
                </select>
                </td>
				<td class="th1" align="right" width="20%" style="height: 30px;">&nbsp; 文件生成日期：</td>
				<td align="left" >
					<input id="statd" value="" name="statd"  class="Wdate" type="text"  onfocus="ryt_area_date('statd','endd',0,30,0)"/>
					&nbsp;&nbsp;
					至&nbsp;&nbsp;<input id="endd" value="" name="endd"  class="Wdate" type="text" disabled="disabled" />
					<font color="red">*</font>
				</td>
				
			</tr>
			<tr>
				<td colspan="4"  align="center">
				<input type="hidden"  name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                 <input type="hidden"  name="operId" id="operId" value="${sessionScope.SESSION_LOGGED_ON_USER.operId}" />
					<input type="button" class= "button" value="查询" onclick = "querylist(1);" />
				</td>
			</tr>
		</tbody>
	</table>
	<table class="tablelist tablelist2" style="display: none;" id="tradelogTable">
		<tr>
			<th>序号</th>
			<th>交易数据来源</th>
			<th>文件名</th>
			<th>生成时间</th>
		</tr>
		<tbody id="resultList">
		</tbody>
	</table>
	
	</div>   
    </body>
</html>
