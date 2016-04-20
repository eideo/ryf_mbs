<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>pos当天交易查询</title>
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
  		<script type="text/javascript" src='../../dwr/interface/QueryPosMerTodayService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/transaction/mer_queryPosTodayTrade.js?<%=rand%>"></script>
    </head>
    <body onload="init();">
    <div class="style">
        <form name="queryForm">
         <table width="100%"  align="left"  class="tableBorder">
            <tr><td class="title" colspan="6">&nbsp;POS当天交易查询</td></tr>
            <tr>
                <td class="th1" bgcolor="#D9DFEE" align="right">电银流水号： </td>
                <td align="left"> <input type="text" id="deduct_sys_stance" name="deduct_sys_stance"  maxlength="30"/></td>
                 <td class="th1" bgcolor="#D9DFEE" align="right" width="10%">电银终端号：</td>
                  <td align="left" width="20%">
                	<input type="text" id="innerTermId" name="innerTermId" maxlength="30" />
                </td>
                 <td class="th1" bgcolor="#D9DFEE" align="right">交易类型：</td>
                <td align="left">
                	<select id="type" name = "type">
                		<option value="">全部...</option>
                	</select>
                </td>
            </tr>
            <tr>
                <td class="th1" bgcolor="#D9DFEE" align="right">交易状态：</td>
                <td align="left">
                	<select id="tstat" name = "tstat">
                	</select>
                </td>
                <td class="th1" bgcolor="#D9DFEE" align="right">银联参考号： </td>
                <td align="left"> <input type="text" id="xpe_deduct_refer" name="xpe_deduct_refer" /></td>
            </tr>
            <tr>
            <td colspan="6" align="center" style="height: 30px">
                <input type="hidden" name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                <input class="button" type="button" value = " 查 询 " onclick="queryMerToday(1);" />
                  &nbsp; &nbsp; 
                <input class="button" type="button" value= " 下载XLS " onclick = "downloadDetail(${sessionScope.SESSION_LOGGED_ON_USER.operId});"/>
            </td>
            </tr>
        </table>
       </form>
    
       <table  class="tablelist tablelist2"  id="merTodayTable" style="display:none;">
           <thead>
           <tr>
              <th>电银交易编号</th>
               <th>商户号</th>
              <th>电银终端号</th>
              <th>电银流水号</th>
              <th>交易金额(元)</th> 
              <th>系统手续费(元)</th>
              <th>交易状态</th>
              <th>系统日期</th>
              <th>银联参考号</th>
             <th>交易类型</th> 
             <th>操作</th>
           </tr>
           </thead>
           <tbody id="resultList"></tbody>
       </table>
       <!-- 详情页面 -->
        <table class="tableBorder detailBox"  id="detailInfo" style="display: none;">
         <tr style="height: 20px">
            	<td class="th1" align="right" width="12%"  >商户号：</td>
                <td  align="left" id="q_xpep_mercode" width="20%"></td>
                <td class="th1" align="right"  width="10%">电银终端号：</td>
                <td align="left" id="q_inner_termId" > </td>
                <td class="th1" align="right" width="15%" >电银交易编号：</td>
                <td  align="left" id="q_trade_no"  width="20%"> </td>
                </tr>
           <tr style="height: 20px">
                <td class="th1" align="right" width="10%">电银流水号：</td>
                <td width="20%" align="left" id="q_xpe_deduct_trace"  > </td>
                <td class="th1" align="right" >交易金额(元)：</td>
                <td  align="left" id="q_amount"  > </td>
                <td class="th1" align="right" >系统手续费(元)：</td>
                <td  align="left" id="q_fee_amt" > </td>
            </tr>
             <tr style="height: 20px">
               <td class="th1" align="right"  >交易类型：</td>
                <td align="left" id="q_type" > </td>
                <td class="th1" align="right"  >交易状态：</td>
                <td   align="left" id="q_tstat"  > </td>
                <td class="th1" align="right"  >系统日期：</td>
                <td align="left" id="q_sys_date" > </td>
            </tr>
             <tr style="height: 20px">
                <td class="th1" align="right"  >银行对账状态：</td>
                <td   align="left" id="q_bankStat" > </td>
                <td class="th1" align="right"  >清算批次号：</td>
                <td align="left" id="q_bath" > </td>
                 <td class="th1" align="right"  >银联参考号：</td>
                <td align="left" id="q_xpe_deduct_refer" > </td>
            </tr>
             <tr style="height: 20px">
                <td class="th1" align="right"  >银行流水2：</td>
                <td align="left" id="q_bk_seq2" > </td>
                <td class="th1" align="right"  >银行返回码：</td>
                <td align="left" id="q_xpe_deduct_resp" > </td>
                <td class="th1" align="right"  >银行流水1：</td>
                <td align="left" id="q_bk_seq1" > </td>
            </tr>
             <tr style="height: 20px">
                <td class="th1" align="right" >描述</td>
                <td align="left"  id="q_trade_name" colspan="5"> </td>
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
