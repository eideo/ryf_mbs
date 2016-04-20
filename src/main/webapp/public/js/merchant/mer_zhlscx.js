//简短说明
var remarkMap ={1:"消费(支付)",2:"退款(冲正)",3:"差错调整(支付)",4:"差错调整(冲正)",5:"结算到电银账户",6:"结算到银行账户",7:"手工调整"};
var zh_flag =null;
function init(){
//	if(zh_flag==null){
//		 MerZHService.getZH2(function(zh){
//			 dwr.util.addOptions("aid", zh);
//			zh_flag=zh;
//		 })
//	    }
	$("bdate").value = jsToday();
	$("edate").value = jsToday();
}

//查询账户交易流水
function queryLS(pageNo){
//	var aid=$("aid").value;
	var bdate=$("bdate").value;
	var edate=$("edate").value;
	if(bdate=='') {
        alert("请选择起始日期！"); 
        return false; 
    }
    if(edate==''){
         alert("请选择结束日期！"); 
         return false; 
    }
    MerZHService.queryLS(pageNo,bdate,edate,callBack);
}
var currPage=1;
var callBack = function(pageObj){
	   $("LSTable").style.display="";
	   var cellFuncs = [
                        function(obj) { return obj.uid; }, //商户号-用户uid
//	                    function(obj) { return obj.aid; }, //账户id
	                    function(obj) { return obj.trDate; },
	                    function(obj) { return getStringTime(obj.trTime); },
	                    //收支标识
	                    function(obj) {
	                    	if(div100(obj.amt) <0 ){
	                    		return "减少";
	                    	}else{
	                    		return "增加";
	                    	}
//	                    	return rec_pay[obj.recPay]; 
	                    	},
	                    function(obj) { return div100(obj.trAmt); },
	                    function(obj) { return div100(obj.trFee); },
	                    function(obj) { return div100(obj.amt); },
//	                    function(obj) { return div100(obj.balance); },//可用余额
	                    function(obj) { return div100(obj.allBalance+obj.balance); },
	                    //电银流水号
	                    function(obj) { return obj.tbId; },
	                    //function(obj) { return obj.trFlag; },
	                    function(obj) { return remarkMap[obj.remark]; }
	                ];	
	                currPage=pageObj.pageNumber; 
	   			paginationTable(pageObj,"resultList",cellFuncs,"","queryLS");
  }

//商户流水查询---下载查询结果
function downloadLS(operId){
//	var aid=$("aid").value;
	var bdate=$("bdate").value;
	var edate=$("edate").value;
	if(bdate=='') {
        alert("请选择起始日期！"); 
        return false; 
    }
    if(edate==''){
         alert("请选择结束日期！"); 
         return false; 
    }
    MerZHService.downloadLS(operId,bdate,edate,function(data){
	      	if (data == null) {
			alert("稍后到下载页面下载数据！");
		}
});
//    dwr.engine.setAsync(false);//把ajax调用设置为同步
//    MerZHService.downloadLS(operId,aid,bdate,edate,
//    				function(data){dwr.engine.openInDownload(data);})
}