
	    
 function init(){
	 CommonService.getMerGatesMap($("mid").value,function(map){
		 dwr.util.addOptions("gate", map);
		 h_gate=map;
	});
     dwr.util.addOptions("tstat", h_tstat);
     dwr.util.addOptions("type", h_type);
 }
 var mid,oid,gate,tstat,type,bkseq,tseq,begintrantAmt,endtrantAmt,operId;
 function judgeCondition(){
      mid = $("mid").value;
      oid = $("oid").value;
      operId = $("operId").value;
     // gate = $("gate").value;
      tstat = $("tstat").value;
      type = $("type").value;
      tseq = $("tseq").value;
      if (tseq != "") {
			tseq = tseq.trim();
			if (!isNumber(tseq)) {
				alert("电银流水号必须是数字!");
				$("tseq").value = '';
				$("tseq").focus();
				return false;
			}
		}
	return true;
}
 // 查询
 function queryMerToday(pageNo){
	 if(!judgeCondition())return;
		jQuery("#toggleAll").attr("checked",false);
	 QueryMerMerTodayService.queryMerToday(pageNo,mid,gate,tstat, type,tseq,oid,null,"",callBack2);

 }
 //下载
 function downloadToday(){
	  if(!judgeCondition())return;
  	  dwr.engine.setAsync(false);//把ajax调用设置为同步
  	QueryMerMerTodayService.downloadToday_MER(mid,operId,tstat, type,tseq,oid,null,"",
    		       function(data){alert(data);});
}		
 //回调函数
 var query_cash = {};
 var callBack2 = function(pageObj){
	  $("merTodayTable").style.display="";
	   dwr.util.removeAllRows("resultList");
	   if(pageObj==null){document.getElementById("resultList").appendChild(creatNoRecordTr(14));return;}
		 var cellFuncs = [
		                  /*function(obj) { if(obj.tstat == 2 || obj.tstat==3) return '<input name="check" type="checkbox" value="'+obj.tseq+'">';
		                  				  else return '';},*/
		                  function(obj) { return obj.tseq; },
		                  function(obj) { return obj.mid; },
		                  function(obj) { return obj.oid; },
		                  function(obj) { return formatDate(obj.mdate); },
		                  function(obj) { return div100(obj.amount); },
		                  function(obj) { return h_tstat[obj.tstat]; },
		                  function(obj) { return h_type[obj.type]; },
		                  function(obj) { return div100(obj.feeAmt); },
		                  function(obj) { return formatDate1(obj.sysDate11);},
		                  function(obj) {
		                     // if(obj.tstat == 2 || obj.tstat==3)
		                    return "<input type=\"button\" value=\" 详情 \" onclick=\"sendRequests('" + obj.tseq+ "')\" >";
		                   // else return '';
		                      }
		              ];
		 for(var i=0;i<pageObj.pageItems.length;i++){
				var o = pageObj.pageItems[i];
	   	        query_cash[o.tseq] = o;
	   	  
			}
		 
      str = "交易总金额：<font color='red'><b>" + div100(pageObj.sumResult.amtSum)
       	   +"</b></font>  元&nbsp;&nbsp;系统手续费总金额：<font color='red'><b>"+ div100(pageObj.sumResult.sysAmtFeeSum)+"</b></font>  元</span>";
       paginationTable(pageObj,"resultList",cellFuncs,str,"queryMerToday");
   };
 //商户请求后台
 function sendRequests(tseq){
	 	//useLoadingImage("../../public/images/wbox/loading.gif");
	 	//QueryMerMerTodayService.notifyMerBkUrl(tseq,t,function(msg){
	 //Object obj=query_cash[tseq];
	 		//queryMerToday(1);
 		//});
	 $("v_mid").innerHTML= query_cash[tseq].mid;
	 $("v_org_oid").innerHTML= query_cash[tseq].oid;
	 $("v_mdate").innerHTML= formatDate(query_cash[tseq].mdate);
	 $("v_tseq").innerHTML= query_cash[tseq].tseq;
	 $("v_amount").innerHTML= div100(query_cash[tseq].amount);
	 $("v_mer_fee").innerHTML= div100(query_cash[tseq].feeAmt);
	 $("v_type").innerHTML= h_type[query_cash[tseq].type];
	 $("v_tstat").innerHTML= h_tstat[query_cash[tseq].tstat];
	 $("v_sys_date").innerHTML= formatDate1(query_cash[tseq].sysDate11);
	 $("v_bk_seq1").innerHTML= query_cash[tseq].bk_seq1;
	 $("v_bk_seq2").innerHTML= query_cash[tseq].bk_seq2;
	 $("v_bankStat").innerHTML= "初始化（未对账）";
          
            //innerVal(query_cash[obj]);
         jQuery("#detail4One").wBox({title:"&nbsp;&nbsp;当天收款交易查询详情",show:true});//显示box

 }
 
//对象显示在表格中
 function innerVal(obj){

 	for (var attr in obj){
 		if(document.getElementById("v_"+attr)==null)continue;
 		if(obj[attr]==null){
 			document.getElementById("v_"+attr).innerHTML="";}
 		else{
 			document.getElementById("v_"+attr).innerHTML=obj[attr];}
 	}
 }
 
 /**
	 * @param status
	 * 批量启用
	 */
	function batchNotifyMerBkUrl(){
		var tseqs = new Array();
		var checkboxes=document.getElementsByName("check");
		for(var i = 0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
				tseqs.push(checkboxes[i].value);
			}
		}
		if(!tseqs.length){
			alert('请选择要通知的交易');
			return;
		}
	 	useLoadingImage("../../public/images/wbox/loading.gif");
	 	QueryMerMerTodayService.batchNotifyMerBkUrl(tseqs,'tlog',function(msg){
			alert(msg);
			queryMerToday(1);
		});
	}
  
 /**
 * @param o 
 * 全选
 */
	function toggleAll(o){
		var status = o.checked;
		var checkboxes=document.getElementsByName("check");
		for(var i = 0;i<checkboxes.length;i++){
			checkboxes[i].checked=status;
		}
	} 

 /****
  * 选择交易类型  交易银行联动功能
  * 
  * 
  */    
function  onChangeGate(){
	 var t = $("type").value;
	 if(t==''){
		 t=-1;
	 }
	 PageService.getGateChannelMapByType(t,function(m){
			h_gate=m[0];
		/*	gate_route_map=m[1];*/
			if($("gate")){
				dwr.util.removeAllOptions("gate");
				dwr.util.addOptions("gate",{'':'全部...'});
				dwr.util.addOptions("gate", h_gate);
			}
		/*	if($("gateRouteId")){
				dwr.util.removeAllOptions("gateRouteId");
				dwr.util.addOptions("gateRouteId",{'':'全部...'})
				dwr.util.addOptions("gateRouteId", gate_route_map);
			}*/
		 
	 });
} 
 