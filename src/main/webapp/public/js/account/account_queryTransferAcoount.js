//转账状态
var stateMap ={S:"转账成功",F:"转账失败"};
// 验证查询条件
var transId,inAccId,bdate, edate, state;
function judgeCondition() {
	transId = $("transId").value;
	inAccId = $("inAccId").value;
	bdate = $("bdate").value;
	edate = $("edate").value;
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
                     //交易流水号
	                 function(obj) {return obj.transId;},
	                //转账金额(元)
	                 function(obj) {return obj.transAmt;},
	                //手续费(元)
	                 function(obj) {return obj.fee;},
	               //交易对方账号
	                 function(obj) {return obj.trader;},
	             	//转账时间
	                 function(obj) {return obj.transTime;},
	               //转账状态
	                 function(obj) {return stateMap[obj.state];},
	               //备注
	                 function(obj) {return obj.remark;}
	                 ];
	paginationTable(pageObj,"resultList",cellFuncs, "", "queryPageTransferInfo");
}

// 转账记录查询
function queryPageTransferInfo(pageNo) {
	if (!judgeCondition())
		return;
	 TransactionService.queryAccTransferInfo(pageNo,transId,inAccId,bdate, edate, state,callBack);
}

//转账记录下载
function downloadDetail(operId){
    if(!judgeCondition())return;
    dwr.engine.setAsync(false);//把ajax调用设置为同步
    TransactionService.downloadDetail_ZZJL(operId,transId,inAccId,bdate,edate,state,
    		function(data){
    		dwr.engine.openInDownload(data);
    });
}

