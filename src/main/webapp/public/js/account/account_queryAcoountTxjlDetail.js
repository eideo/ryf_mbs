// 验证查询条件
var bdate, edate, oid, state;
function judgeCondition() {
	bdate = $("bdate").value;
	edate = $("edate").value;
	oid = $("oid").value;
	state = $("state").value;
	if (bdate == '') {
		alert("请选择起始日期!");
		return false;
	}
	if (edate == '') {
		alert("请选择结束日期!");
		return false;
	}
	return true;
}

var callBack = function(pageObj) {
	if (pageObj == null)
		document.getElementById("resultList").appendChild(creatNoRecordTr(9));
	var cellFuncs = [
	                 function(obj) {return obj.oid;},
	                 function(obj) {return obj.date;},
	                 function(obj) {return obj.amount;},
	                 function(obj) {return obj.fee;},
	                 function(obj) {return obj.amount;},
	                 function(obj) {return obj.state;},
	                 function(obj) {return obj.date;},
	                 function(obj) {return obj.failReason;},
	                 function(obj) {return obj.remark;}
	                 ];
	paginationTable(pageObj,"resultList",cellFuncs, "", "queryPageTxjl");
}

// 充值记录查询
function queryPageTransferInfo(pageNo) {
	if (!judgeCondition())
		return;
	 TransactionService.queryAccountTxjlDetail(pageNo, bdate, edate, oid, state, callBack);
}

//充值记录下载
function downloadDetail(operId){
    if(!judgeCondition())return;
    dwr.engine.setAsync(false);//把ajax调用设置为同步
    TransactionService.downloadDetail_TXJL(operId, bdate,edate,oid,state,
    		function(data){
    		dwr.engine.openInDownload(data);
    });
}