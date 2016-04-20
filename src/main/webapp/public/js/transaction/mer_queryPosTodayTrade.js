function init(){
    dwr.util.addOptions("tstat", {"":"全部","00":"成功",N1:"超时",N2:"失败"});
    dwr.util.addOptions("type", initPosType);
}
var initPosType = {
		"":"全部",
		"19":"消费",
		"20":"消费冲正",
		"21":"消费撤销",
		"22":"消费撤销冲正",
		"23":"退货",
		"24":"预授权完成",
		"25":"预授权完成撤销",
		"26":"预授权完成冲正",
		"27":"预授权完成撤销冲正",
		"28":"脱机消费",
		"31":"预授权",
		"32":"预授权冲正",
		"33":"预授权撤销",
		"34":"预授权撤销冲正",
		"35":"信用卡还款",
		"36":"未知",
	};
//交易状态
var tstatMap={"00":"成功",N1:"超时",N2:"失败"};
//pos交易类型
var posTypeMap = {
		"":"全部",
		"19":"消费",
		"20":"消费冲正",
		"21":"消费撤销",
		"22":"消费撤销冲正",
		"23":"退货",
		"24":"预授权完成",
		"25":"预授权完成撤销",
		"26":"预授权完成冲正",
		"27":"预授权完成撤销冲正",
		"28":"脱机消费",
		"29":"转账汇款",
		"30":"账户支付",
		"31":"预授权",
		"32":"预授权冲正",
		"33":"预授权撤销",
		"34":"预授权撤销冲正",
		"35":"信用卡还款",
		"36":"未知",
	};

function onlyNu(o){
	if(!isNumber(o.value)){
		o.value="";
	}
}
 // 查询
 function queryMerToday(pageNo){
	 var mid = $("mid").value;
	 var deduct_sys_stance = $("deduct_sys_stance").value; //电银流水号
	 var innerTermId=$("innerTermId").value;//电银终端号
	 var type=$("type").value;//交易类型
	 var tstat = $("tstat").value; //交易状态
	 var xpe_deduct_refer = $("xpe_deduct_refer").value; //银联参考号
	 QueryPosMerTodayService.queryPosMerToday(pageNo,15,mid,deduct_sys_stance,innerTermId,type,tstat,xpe_deduct_refer,callBack2);	
 }
 
 //回调函数
 var query_detail = {};
 var callBack2 = function(pageObj){
	  $("merTodayTable").style.display="";
	   dwr.util.removeAllRows("resultList");
	   if(pageObj==null){document.getElementById("resultList").appendChild(creatNoRecordTr(17));return;}
	   var cellFuncs = [
                function(obj) { return obj.tseq; },
                function(obj) { return obj.xpepMercode; },   //商户号
                function(obj) { return obj.innerTermId;},
                function(obj) { return obj.xpeDeductTrace == null ? addNumber(obj.xpepTrace):obj.xpeDeductTrace;},//电银流水号  xpeDeductTrace 
                function(obj) { return div100(obj.amount); },
                function(obj) { return obj.merFeeModel;},//系统手续费obj.xpepTrademsgType
                function(obj) { return tstatMap[obj.tstat]; },
                function(obj) { return formatDate1(obj.sysDate);},
                function(obj) { return obj.xpeDeductRefer;},//银联参考号    xpeDeductRefer
                function(obj) { return posTypeMap[obj.type];},//描述<交易类型>
                function(obj) { 
                return "<input type=\"button\" value=\" 详情 \" onclick=\"queryDetails('" + obj.tseq+ "')\" >";
                }
              ];
			   for(var i=0;i<pageObj.pageItems.length;i++){
				   var o = pageObj.pageItems[i];
				   query_detail[o.tseq] = o;
		
				   }
	              str ="<span class='pageSum'>&nbsp;交易总金额：<font color='red'><b>" + div100(pageObj.sumResult["amtSum"])+"</b></font>  元 </span>" +
            		" <span class='pageSum'>&nbsp;&nbsp;系统手续费总金额：<font color='red'><b>"+ div100(pageObj.sumResult["sysAmtFeeSum"])+"</b></font>  元</span>";
       paginationTable(pageObj,"resultList",cellFuncs,str,"queryMerToday");
   };
   
   //点击详情，查看当前商户的详细信息
   function queryDetails(tseq){
	   //商户号 
	   $("q_xpep_mercode").innerHTML= query_detail[tseq].xpepMercode;
	   //电银交易编号
	   $("q_trade_no").innerHTML= query_detail[tseq].tseq;
	   //电银流水号 
	   $("q_xpe_deduct_trace").innerHTML= query_detail[tseq].xpeDeductTrace;
	   //交易金额
	   $("q_amount").innerHTML= div100(query_detail[tseq].amount);
	   //系统手续费
	   $("q_fee_amt").innerHTML= query_detail[tseq].merFeeModel;
	   //电银终端号 
	   $("q_inner_termId").innerHTML= query_detail[tseq].innerTermId;
	   //交易状态 
	   $("q_tstat").innerHTML= tstatMap[query_detail[tseq].tstat];
	   //系统日期
	   $("q_sys_date").innerHTML= formatDate1(query_detail[tseq].sysDate);
       // 交易类型 
	   $("q_type").innerHTML= posTypeMap[query_detail[tseq].type];
	   //银联参考号  
	   $("q_xpe_deduct_refer").innerHTML= [query_detail[tseq].xpeDeductRefer];
	   //银行对账状态
	   $("q_bankStat").innerHTML= "初始化（未对账）";
	   //银行流水1
	   $("q_bk_seq1").innerHTML= query_detail[tseq].bk_seq1;
	   //银行返回代码 
	   $("q_xpe_deduct_resp").innerHTML=query_detail[tseq].xpeDeductResp;
	   //描述 kong
	   $("q_trade_name").innerHTML= "";
	   
       jQuery("#detailInfo").wBox({title:"&nbsp;&nbsp;pos当天交易查询信息详情",show:true});//显示box
   }
   
 //对象显示在表格中
   function innerVal(obj){
   	for (var attr in obj){
   		if(document.getElementById("q_"+attr)==null)continue;
   		if(obj[attr]==null){
   			document.getElementById("q_"+attr).innerHTML="";}
   		else{
   			document.getElementById("q_"+attr).innerHTML=obj[attr];}
   	}
  }
   
   function subBankNo(str) {
	   	if(null != str && ''!= str){
	   		var index = str.length-4;
	   		var strs1 = str.substring(0, 6);
	       	var strs = str.substring(index, str.length);
	       	return strs1 + "******" +strs;
	   	}
	   	return '';
	   }
	   
function addNumber(number) {
	var b = "";
	var len = (number + "").length;
	for (var i = 0; i < 6 - len; i++) {
		b += "0";
	}
	b += number;
	return b;
}

function fontString(str) {
	if (null != str && '' != str) {
		var index = str.length - (str.length - 6);
		var strs1 = str.substring(0, index);
		var strs = str.substring(index, str.length);
		return strs1 + "<font color='red'>" + strs + "</font>";
	}
	return '';
}
	   
// 下载pos历史查询交易报表
function downloadDetail(operId) {
	var mid = $("mid").value;
	var deduct_sys_stance = $("deduct_sys_stance").value; //电银流水号
	var tstat = $("tstat").value;
	var type = $("type").value;//交易类型
	var innerTermId = $("innerTermId").value;//电银终端号
	 var xpe_deduct_refer=$("xpe_deduct_refer").value;  //银联参考号
	 
QueryPosMerTodayService.downloadToday(operId, mid, deduct_sys_stance, innerTermId, type,tstat,xpe_deduct_refer,
			function(data) {
				if (data == null) {
					alert("稍后到下载页面下载数据！");
				}
			});
}
