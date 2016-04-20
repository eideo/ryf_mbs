<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="com.rongyifu.mms.bean.LoginUser"%>
<%@page import="com.rongyifu.mms.web.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<title>操作员密码修改</title>
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
        <link href="../../public/css/head.css?<%=rand%>" rel="stylesheet" type="text/css"/>
		<script type='text/javascript' src='../../dwr/engine.js'></script>
		 <script type="text/javascript" src="../../public/js/ryt_util.js?<%=rand%>"></script>
		
		<script type="text/javascript" src="../../public/js/md5.js"></script>
		<script type='text/javascript' src='../../dwr/interface/MerMerchantService.js?<%=rand%>'></script>
			<script >
		// 用正则表达式将前后空格，用空字符串替代。
	String.prototype.trim = function(){
	    return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	function edit_pass(mid) {
		var oper_id = document.getElementById("oper_id").value;
		var opass = document.getElementById("opass").value;
		var npass = document.getElementById("npass").value;
		var vnpass = document.getElementById("vnpass").value;
		if(npass.trim()==''){
			alert("密码不能为空或空格！");
			return false;
		}
		if(npass.length<8||npass.length>15){
			alert("密码长度在8-15位！");
			return false;
		}
		if(vnpass==''){
			alert("请确定新密码！");
			return false;
		}
		if(npass!=vnpass){
			alert("两次密码不一致！");
			return false;
		}
		if(!isCharAndNum(npass)){
			alert("新密码中必须包含字母、数字和特殊字符！");
			return false;
		}
		if(opass==npass){
			alert("新密码不能和原密码一致！");
			return false;
		}
		MerMerchantService.editPass(mid,oper_id,hex_md5(opass),hex_md5(npass) ,callback);
	}
	function init(){
	/*  alert(document.getElementById("opass").value);  */
     	document.getElementById("opass").value = "";
     	
     	 
     
        }
	
	function callback(msg) {
		alert(msg);
		document.getElementById("opass").value = "";
	    document.getElementById("npass").value = "";
	    document.getElementById("vnpass").value = "";
	}
		</script>
	</head>

	<body onload="init();"> 
	 <div class="style">
		
		<%
		    LoginUser user = (LoginUser)session.getAttribute(WebConstants.SESSION_LOGGED_ON_USER);  
            if(user==null) response.sendRedirect(request.getContextPath()+"/login.jsp");
			String mid = user.getMid()+"";
			String uid = user.getOperId()+"";
			%>
			<table class="tableBorder" >
		<tbody>
			<tr>
				<td colspan="2" align="left" height="25" class="title">
							<b><font color="#ffffff">&nbsp;&nbsp;&nbsp;&nbsp;操作员密码修改 </font> </b>
						</td>
					</tr>
			
					<tr>
						<td width="30%" align="right" class="th1">
							&nbsp;商户：
						</td>
						<td width="70%" align="left">
							&nbsp;&nbsp;<%=mid%>
							
						</td>
					</tr>
					<tr>
						<td width="30%" align="right" class="th1">
							&nbsp;操作员号：
						</td>
						<td width="70%" align="left">
							&nbsp;
							<%=uid %>
							<input type="hidden" id="oper_id" value="<%=uid %>" />
						</td>
					</tr>
					<tr>
						<td width="30%" align="right" class="th1">
							&nbsp; 原密码：
						</td>
						<td width="70%" align="left">
							&nbsp;
							<input type="password" id="opass"  value="" size="30" maxlength="15"/>
						</td>
					
					</tr>
					<tr>
						<td width="30%" align="right" class="th1">
							&nbsp; 新密码：
						</td>
						<td width="70%" align="left">
							&nbsp;
							<input type="password" id="npass" value="" size="30" maxlength="15"/><font color="red" >（8-15位长度，必须由数字、字母和特殊字符组成）</font>
						</td>
					</tr>
						
					<tr>
						<td width="30%" align="right" class="th1">
							&nbsp; 确定新密码：
						</td>
						<td width="70%" align="left">
							&nbsp;
							<input type="password" id="vnpass" value="" size="30" maxlength="15"/>
						</td>
					</tr>
				
					<tr>
						<td colspan="2" height="30px" align="center">
							<input type="button" class="button" value="提  交" onclick=" return edit_pass('<%=mid%>')"/>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
	</body>
</html>
