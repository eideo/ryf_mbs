<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>收支明细查询</title>
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
  		<script type="text/javascript" src='../../dwr/interface/MerZHService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/account/acc_payment_detail.js?<%=rand%>"></script>
        <script type='text/javascript' src='../../public/datePicker/WdatePicker.js?<%=rand%>'></script>
    </head>
    <body onload="init();">
     <div class="style">
	<table class="tableBorder">
		<tbody>
			<tr>
				<td class="title" colspan="16">&nbsp;&nbsp; 收支明细查询
				</td>
			</tr>
			<tr>
				<td class="th1" align="right" width="20%" style="height: 30px;">&nbsp; 交易日期：</td>
				<td align="left" >
					<input id="startDate" value="" name="startDate"  class="Wdate" type="text"  onfocus="ryt_area_date('startDate','endDate',0,30,0)"/>
					&nbsp;&nbsp;
					至&nbsp;&nbsp;<input id="endDate" value="" name="endDate"  class="Wdate" type="text" disabled="disabled" />
					<font color="red">*</font>
				</td>
				<td class="th1" bgcolor="#D9DFEE" align="right" width="11%">收支类型：</td>
                <td align="left" width="20%">
                <select style="width: 150px" id="flag" name="flag">
                    <option value="">全部...</option>
                    <option value="1">增加</option>
                    <option value="2">减少</option>
                </select>
                </td>
			</tr>
			<tr>
				 <td class="th1" bgcolor="#D9DFEE" align="right">交易流水：</td>
                <td align="left"><input type="text" id="tseq" name="seqNo" id="seqNo"/></td>
				<td class="th1" bgcolor="#D9DFEE" align="right" width="11%">交易类型：</td>
                <td align="left" width="20%">
                <select style="width: 150px" id="type" name="type">
                </select>
                </td>
			</tr>
			<tr>
				<td colspan="4"  align="center">
				<input type="hidden"  name="accId" id="accId" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                 <input type="hidden"  name="operId" id="operId" value="${sessionScope.SESSION_LOGGED_ON_USER.operId}" />
					<input type="button" class= "button" value="查询" onclick = "querylist(1);" />
					<input type="button" class= "button" value="下载" onclick = "download();" />
				</td>
			</tr>
		</tbody>
	</table>
	<table class="tablelist tablelist2" style="display: none;" id="tradelogTable">
		<tr>
			<th>交易流水号</th>
			<th>交易时间</th>
			<th>收支类型</th>
			<th>交易金额(元)</th>
			<th>手续费(元)</th>
			<th>变动金额(元)</th>
			<th>可用余额(元)</th>
			<th>冻结金额(元)</th>
			<th>总余额(元)</th>
			<th>交易类型</th>
		</tr>
		<tbody id="resultList">
		</tbody>
	</table>
	</div>   
    </body>
</html>
