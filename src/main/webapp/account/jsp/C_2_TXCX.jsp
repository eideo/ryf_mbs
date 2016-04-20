<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>提现记录查询</title>
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
        <script type='text/javascript' src='../../dwr/engine.js?<%=rand%>'></script>
        <script type='text/javascript' src='../../dwr/util.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/CommonService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/TransactionService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/datePicker/WdatePicker.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
  		<script type="text/javascript" src="../../public/js/account/account_queryAcoountTxjlDetail.js?<%=rand%>"></script>
    </head>
    <body onload="init()" >
    
     <div class="style">
        <form name="MERHLOG"  method="post" action="">
         <table width="100%"  align="left"  class="tableBorder">
            <tr><td class="title" colspan="6">&nbsp;&nbsp;&nbsp;&nbsp;提现记录查询&nbsp;&nbsp;</td></tr>
            <tr>
            
             <td class="th1" bgcolor="#D9DFEE" align="right" width="10%">提现日期：</td>
                <td align="left"  width="30%">
                    <input id="bdate" size="13px"
                    name="bdate" class="Wdate" type="text" onfocus="ryt_area_date('bdate','edate',0,15,0)" />
                    &nbsp;至&nbsp;
                    <input id="edate" size="13px" name="edate" class="Wdate" type="text" disabled="disabled" /><font color="red">*</font>
                </td> 
                <td class="th1" bgcolor="#D9DFEE" align="right"  width="7%">商户订单号： </td>
                <td align="left"> <input type="text" id="oid" name="oid"  maxlength="30"/></td>
                <td class="th1" bgcolor="#D9DFEE" align="right" width="6%" >充值状态：</td>
                <td align="left" width="20%" >
                    <select style="width: 130px" id="state" name="state">
                        <option value="">全部</option>
                        <option value="1">交易成功</option>
                        <option value="2">交易失败</option>
                        <option value="3">待支付</option>
                        <option value="4">处理中</option>
                    </select>
                </td>
            </tr>
            <tr>
            <td colspan="6" align="center" style="height: 30px">
                <input type="hidden"  name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                <input type="hidden"  name="queryType" id="queryType" value="MERHLOG" />
                <input class="button" type="button" value = " 查 询 " onclick="queryPageTxjl(1);" />
                 &nbsp; &nbsp; 
                <input class="button" type="button" value= " 下载XLS " onclick = "downloadDetail(${sessionScope.SESSION_LOGGED_ON_USER.operId });"/>
            </td>
            </tr>
        </table>
       </form>
    
       <table  class="tablelist tablelist2" id="merHlogTable">
           <thead>
           <tr>
             <th>电银流水号</th><th>商户订单号</th>
             <th>提现金额(元)</th><th>手续费(元)</th>
             <th>实际提现金额(元)</th><th>交易状态</th>
             <th>订单时间</th><th>失败原因 </th><th>备注</th>
           </tr>
           </thead>
           <tbody id="resultList"></tbody>
       </table>
      </div>  
    </body>
</html>
