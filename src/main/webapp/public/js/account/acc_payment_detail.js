 function init(){
	
     dwr.util.addOptions("type", acc_type);
 }

// 验证查询条件

var startDate, endDate, flag,tseq, type,accId;
function judgeCondition() {
	startDate = $("startDate").value;
	endDate = $("endDate").value;
	flag = $("flag").value;
	tseq = $("tseq").value;
	type = $("type").value;
	accId = $("accId").value;
	if (startDate == '') {
		alert("请选择起始日期!");
		return false;
	}
	if (endDate == '') {
		alert("请选择结束日期!");
		return false;
	}
	return true;
}

var callBack = function(pageObj) {
	 $("tradelogTable").style.display="";
	if (pageObj == null)
		document.getElementById("resultList").appendChild(creatNoRecordTr(10));
	var cellFuncs = [
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;},
	                 function(obj) {return obj.seqNo;}
	                 ];
	paginationTable(pageObj,"resultList",cellFuncs, "", "querylist");
};

// 收支明细查询
function querylist(pageNo) {
	if (!judgeCondition())
		return;
	MerZHService.queryAccPayDetail(pageNo, startDate, endDate, flag, tseq,type,accId, callBack);
}

function download(){
	if(!judgeCondition())return;
    dwr.engine.setAsync(false);//把ajax调用设置为同步
    MerZHService.downloadAccPayDetail(startDate, endDate, flag, tseq,type,accId,
    			function(data){dwr.engine.openInDownload(data);});
}