<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>账户信息查询</title>
		 <%
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		int rand = new java.util.Random().nextInt(10000);
		%>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="Expires" content="0"/>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link href="../../public/css/head.css?v=" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="../../dwr/engine.js"></script>
        <script type="text/javascript" src="../../dwr/util.js"></script>
        <script type="text/javascript" src="../../dwr/interface/PageService.js?<%=rand %>"></script>
		<script type="text/javascript" src="../../dwr/interface/MerAccountService.js?<%=rand %>"></script>
		<script type="text/javascript" src='../../dwr/interface/PageParam.js?<%=rand %>'></script>
        <script type="text/javascript" src='../../public/js/ryt.js?<%=rand %>'></script>
        <script type="text/javascript" src='../../public/js/merchant/account_queryzhInfo.js?<%=rand%>'></script>
    </head>
    <body onload="init('${sessionScope.SESSION_LOGGED_ON_USER.mid}');">
    <div class="style">
        
		<table class="tableBorder">
			<tbody>
				<tr><td class="title" colspan="6">&nbsp;&nbsp;基本信息</td></tr>
				<tr>
                    <td class="th1" align="right" width="11%">&nbsp;账户号：</td>
                    <td width="22%" align="left" id="v_accId" ></td>
                    <td class="th1" align="right" width="11%">&nbsp;姓名/企业名称：</td>
                    <td width="22%" align="left" id="v_comName" ></td>
                </tr>
                <tr>
                    
                    <td class="th1" align="right" >&nbsp;账户类型：</td>
                    <td  align="left" id="v_accType" ></td>
                    <td class="th1" align="right" >&nbsp;商户名：</td>
                    <td  align="left" id="v_merName" ></td>
                </tr>
                <tr>
                    <td class="th1" align="right" >&nbsp;手机号码：</td>
                    <td  align="left" id="v_phone" ></td>
                    <td class="th1" align="right" >&nbsp;邮箱：</td>
                    <td  align="left" id="v_email" ></td>
                </tr>
                <tr>
                   
                    <td class="th1" align="right" >&nbsp;账户状态：</td>
                    <td  align="left" id="v_accState" ></td>
                    <td class="th1" align="right" >证件类型：</td>
                    <td  align="left" id="v_certType" ></td>
                </tr>
                <tr>
                    <td class="th1" align="right" >证件号码：</td>
                    <td  align="left" id="v_certNo" ></td>
                    <td class="th1" align="right" >&nbsp;证件认证状态：</td>
                    <td  align="left" id="v_authType" ></td>
                </tr>
                <tr>
                    <td class="th1" align="right" >注册时间：</td>
                    <td  align="left" id="v_regTime" ></td>
                </tr>
                </tbody>
                </table>
		      <table class="tableBorder" >
                <tbody>
                <tr><td class="title" colspan="6">&nbsp;&nbsp;银行卡信息</td></tr>
                <tr>
                <td class="title" align="center" width="200px">银行卡类型</td>
                <td class="title" align="center" width="200px">开户银行名</td>
                <td class="title" align="center" width="200px">卡号</td>
                <td class="title" align="center" width="200px">预留手机号码</td>
                <td class="title" align="center" width="200px">卡认证状态</td>
                </tr>
                <tr>
                    <td  align="center"  id="v_cardType"></td>
                    <td  align="center"  id="v_bankName"></td>
                    <td  align="center"  id="v_cardNo"></td>
                    <td  align="center"  id="v_bankPhone"></td>
                    <td  align="center"  id="v_cardAuthStatus"></td>
                </tr>
            </tbody>
        </table>
		 </div>
    </body>
</html>
