//admin和jsp中公用  
// 根据条件查询
function queryBaseAdjustAccount(pageNo) {
	var mid = dwr.util.getValue("mid").trim();
	var btdate = dwr.util.getValue("btdate");
	var etdate = dwr.util.getValue("etdate");
	var type = dwr.util.getValue("type");
	if (mid != '' && !isFigure(mid)) {
		alert("商户号只能是整数!");$("mid").value='';
		return false;
	}
	MerSettlementService.queryAdjust(pageNo, mid, type, btdate, etdate, callBackAdjustAccountList);
}
// 提取用户列表的回设函数：adjustaccountList中放的是adjustaccount对象
var callBackAdjustAccountList = function(adjustaccountList) {
		document.getElementById("adjustaccountList").style.display = '';
		var count=1;
	    var cellFuncs = [
	                     function(obj) { return count++ },
	                     function(obj) { return obj.mid; },
	                     function(obj) { return m_minfos[obj.mid]; },
	                     function(obj) { return obj.account / 100; },
	                     function(obj) { return obj.type == 1 ? "手工增加":"手工减少";},
	                     function(obj) { return obj.auditDate==null?"":(obj.auditDate+"  "+getStringTime(obj.auditTime)); },
	                     function(obj) {   return obj.reason;}
	                 ]
	     paginationTable(adjustaccountList,"adjustaccountTable",cellFuncs,"","queryBaseAdjustAccount");	     
}