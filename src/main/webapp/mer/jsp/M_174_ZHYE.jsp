<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>账户余额查询</title>
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
        <script type="text/javascript" src="../../dwr/engine.js?<%=rand %>"></script>
        <script type="text/javascript" src="../../dwr/util.js?<%=rand %>"></script>
        <script type="text/javascript" src='../../dwr/interface/MerZHService.js?<%=rand%>'></script> 
        <script type="text/javascript" src="../../public/js/merchant/mer_zhyecx.js?<%=rand%>"></script>
 		<script type='text/javascript' src='../../public/js/ryt.js?<%=rand%>'></script>
    </head>
    <body onload="initZHYECX();">
    <div class="style">
    
       <table  class="tablelist tablelist2" >
           <thead>
           <tr><td> <input type="hidden"  name="mid" id="mid" value="${sessionScope.SESSION_LOGGED_ON_USER.mid}" /></td> </tr>
           <tr>
             <th>账户号</th><th>可用余额(元)</th><th>冻结余额(元)</th><th>总余额(元)</th>
		   
           </tr>
           </thead>
           <tbody id="resultList">
           </tbody>
       </table>
       
      </div>   
    </body>
</html>

           
           
    
