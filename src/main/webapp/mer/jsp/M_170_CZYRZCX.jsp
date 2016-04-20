<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>操作员日志查询</title>
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
        <script type="text/javascript" src="../../dwr/engine.js?<%=rand%>"></script>
        <script type="text/javascript" src="../../dwr/util.js?<%=rand%>"></script>
        <script type="text/javascript" src='../../dwr/interface/CommonService.js?<%=rand%>'></script>
  		<script type="text/javascript" src='../../dwr/interface/MerMerchantService.js?<%=rand%>'></script>
        <script type="text/javascript" src='../../dwr/interface/PageService.js?<%=rand%>'></script>
		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/merchant/mer_jsp_serch_log.js?<%=rand%>"></script>
        <script type='text/javascript' src='../../public/datePicker/WdatePicker.js?<%=rand%>'></script>
    </head>
    <body>
    <div class="style">
        <form name="MERTLOG"  method="post" action="">
         <table width="100%"  align="left"  class="tableBorder">
           
            <tr>
                <td class="th1" align="right" width="10%">申请日期：</td>
        		 <td align="left">
          			<input id="bdate" size="15px"
                    name="bdate" class="Wdate" type="text" onfocus="ryt_area_date('bdate','edate',0,7,0)" />
                    &nbsp;至&nbsp;
                    <input id="edate" size="15px" name="edate" class="Wdate" type="text" disabled="disabled" /><font color="red">*</font>
        		 </td>  
            </tr>
            <tr>
            <td colspan="6" align="center" style="height: 30px">
                <input type="hidden"  name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" />
                <input type="hidden"  name="operId" id="operId" value="${sessionScope.SESSION_LOGGED_ON_USER.operId}" />
               
                <input class="button" type="button" value = " 查 询 " onclick="getMidOperLog(1);" />
                <!-- 
                <input style="width: 90px;height: 25px;margin-right: 10px" type="button" value = " 下载TXT "  onclick="query('txt', '');"/>
                 -->
                <input class="button" type="button" value= " 下载XLS " onclick = "downloadLog();"/>
            </td>
            </tr>
        </table>
       </form>
    
       <table  class="tablelist tablelist2"  id="merLogTable" style="display:none;">
           <thead>
           <tr>
           
             <th>商户号</th>
             <th>商户简称</th><th>操作员号</th>
             <th>操作员名</th><th>系统日期</th>
             <th>系统时间</th><th>操作员IP</th>
             <th>操作</th>
             <th>操作结果 </th>
           </tr>
           </thead>
           <tbody id="resultList"></tbody>
       </table>
       
      </div>   
    </body>
</html>
