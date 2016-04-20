<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>当天收款交易查询</title>
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
  		<script type="text/javascript" src='../../dwr/interface/QueryMerMerTodayService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/transaction/mer_jsp_queryTodayTrade.js?<%=rand%>"></script>
    </head>
    <body onload="init();">
    <div class="style">
        <form name="MERTLOG"  method="post" action="">
         <table width="100%"  align="left"  class="tableBorder">
            <tr><td class="title" colspan="6">&nbsp;&nbsp;&nbsp;&nbsp;当天收款交易查询</td></tr>
            <tr>
                 <td class="th1" bgcolor="#D9DFEE" align="right">商户订单号： </td>
                <td align="left"> <input type="text" id="oid" name="oid"  maxlength="30"/></td>
                <td class="th1" bgcolor="#D9DFEE" align="right" width="10%">交易状态：</td>
                <td align="left" width="25%">
                <select style="width: 150px" id="tstat" name="tstat" >
                    <option value="">全部...</option>
                </select>
                </td>
                <td class="th1" bgcolor="#D9DFEE" align="right" width="10%" >交易类型：</td>
                <td align="left">
                    <select style="width: 150px" id="type" name="type" onchange="onChangeGate();">
                        <option value="">全部...</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="th1" bgcolor="#D9DFEE" align="right">电银流水号：</td>
                <td align="left"><input type="text" id="tseq" name="tseq" id="tseq"/></td>
            </tr>
            <tr>
            <td colspan="6" align="center" style="height: 30px">
                <input type="hidden"  name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                 <input type="hidden"  name="operId" id="operId" value="${sessionScope.SESSION_LOGGED_ON_USER.operId}" />
                <input type="hidden"  name="queryType" id="queryType" value="MERTLOG" />
                <input class="button" type="button" value = " 查 询 " onclick="queryMerToday(1);" />
                <!-- 
                <input style="width: 90px;height: 25px;margin-right: 10px" type="button" value = " 下载TXT "  onclick="query('txt', '');"/>
                 -->
                <input class="button" type="button" value= " 下载XLS " onclick = "downloadToday();"/>
            </td>
            </tr>
        </table>
       </form>
    
       <table  class="tablelist tablelist2"  id="merTodayTable" style="display:none;">
           <thead>
           <tr>
            <!--  <th sort="false"><input type="checkbox" id="toggleAll" onclick="toggleAll(this)"/>全选</th> -->
             <th>电银流水号</th><th>商户号</th>
             <th>商户订单号</th><th>商户日期</th>
             <th>交易金额(元)</th><th>交易状态</th>
             <th>交易类型</th><!-- <th>交易银行</th> -->
             <th>系统手续费(元)</th><th>系统日期</th>
             <th>操作 </th>
           </tr>
           </thead>
           <tbody id="resultList"></tbody>
       </table>
        <table class="tableBorder detailBox" id="detail4One" style="display: none;">
            <tr style="height: 20px">
            	<td class="th1" align="right" width="12%"  >商户号：</td>
                <td  align="left" id="v_mid" width="20%"></td>
                <td class="th1" align="right" width="10%">商户订单号：</td>
                <td  align="left" id="v_org_oid"> </td>
                <td class="th1" align="right" width="15%" >商户日期：</td>
                <td  align="left" id="v_mdate"  width="20%"> </td>
                
                
            </tr>
            <tr style="height: 20px">
                <td class="th1" align="right" width="10%">电银流水号：</td>
                <td width="20%" align="left" id="v_tseq"  > </td>
                <td class="th1" align="right" >交易金额：</td>
                <td  align="left" id="v_amount"  > </td>
                <td class="th1" align="right" >系统手续费：</td>
                <td  align="left" id="v_mer_fee" > </td>
            </tr>
            <tr style="height: 20px">
                <td class="th1" align="right"  >交易类型：</td>
                <td align="left" id="v_type" > </td>
                <td class="th1" align="right"  >交易状态：</td>
                <td   align="left" id="v_tstat"  > </td>
                <td class="th1" align="right"  >系统日期：</td>
                <td align="left" id="v_sys_date" > </td>
                
            </tr>
           <tr style="height: 20px">
                <td class="th1" align="right"  >银行对账状态：</td>
                <td   align="left" id="v_bankStat" > </td>
                <td class="th1" align="right"  >清算批次号：</td>
                <td align="left" id="v_bath" > </td>
                <td class="th1" align="right"  >银行流水1：</td>
                <td   align="left" id="v_bk_seq1" > </td>
            </tr>
            <tr style="height: 20px">
                <td class="th1" align="right"  >银行流水2：</td>
                <td   align="left" id="v_bk_seq2" > </td>
                <td class="th1" align="right"  >银行返回码：</td>
                <td align="left" id="v_bk_resp" > </td>
                <td class="th1" align="right"  ></td>
                <td   align="left" > </td>
            </tr>
           
            <tr style="height: 30px">
                <td colspan="6" align="center">
                    <input type="button" value="返回" class="wBox_close button"/>
                </td>
            </tr>
    </table>
      </div>   
    </body>
</html>
