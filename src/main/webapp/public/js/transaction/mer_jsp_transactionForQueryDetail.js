   function init(){
	  /* CommonService.getMerGatesMap($("mid").value,function(map){
		   dwr.util.addOptions("gate", map);
		   h_gate=map;
	   });*/
       dwr.util.addOptions("tstat", h_tstat);
       dwr.util.addOptions("type", h_type);
   }
   //查询条件的判断
   var mid,oid,tesq,tstat,type,date,bdate,edate,begintrantAmt,endtrantAmt;
   function judgeCondition(){
        mid = $("mid").value;
        oid = $("oid").value;
        tesq = $("tesq").value;
        tstat = $("tstat").value;
        type = $("type").value;
        date = $("date").value;
        bdate = $("bdate").value;
        edate = $("edate").value;
       if(bdate=='') {
           alert("请选择起始日期！"); 
           return false; 
       }
       if(edate==''){
            alert("请选择结束日期！"); 
            return false; 
       }
       return true;
   }
   
   //商户明细查询 
   function queryMerHlog(pageNo){
	   if(!judgeCondition())return;
	   jQuery("#toggleAll").attr("checked",false);
	   QueryMerMerHlogDetailService.queryHlogDetail(pageNo, mid,tesq,tstat,type,oid,null,date,bdate,edate,null,callBack2);
   }
   
   //商户明细查询下载
   function downloadDetail(operId){
	    if(!judgeCondition())return;
	    dwr.engine.setAsync(false);//把ajax调用设置为同步
	    QueryMerMerHlogDetailService.downloadDetail_MER(operId, mid,tesq,tstat,type,oid,null,date,bdate,edate,
	    				function(data){
//	    		dwr.engine.openInDownload(data);
	    		if (data == null) {
	    			alert("稍后到下载页面下载数据！");
	    		}
	    });
	}
 
   //回调函数
   var query_cash = {};
   var callBack2 = function(pageObj){
	   $("merHlogTable").style.display="";
	   if(pageObj==null)document.getElementById("resultList").appendChild(creatNoRecordTr(12));
	   var cellFuncs = [
	                    function(obj) { return obj.tseq; },
	                    function(obj) { return obj.mid; },
	                    function(obj) { return obj.oid; },
	                    function(obj) { return obj.mdate; },
	                    function(obj) { return div100(obj.amount); },
	                    function(obj) { return h_tstat[obj.tstat]; },
	                    function(obj) { return h_type[obj.type]; },
	                    function(obj) { return div100(obj.feeAmt); },
	                    function(obj) { return formatDate1(obj.sysDate11); },
	                    function(obj) {
	                    	return "<input type=\"button\" value=\" 详细  \" onclick=\"sendRequests('"+obj.tseq+"')\" >";
	                    }
	                ];	
	   
			   for(var i=0;i<pageObj.pageItems.length;i++){
					var o = pageObj.pageItems[i];
		  	        query_cash[o.tseq] = o;
				}
	   
         str = "<span class='pageSum'>&nbsp;&nbsp;交易总金额：<font color='red'><b>" + div100(pageObj.sumResult.amtSum)
         		+"</b></font>  元&nbsp;&nbsp;系统手续费总金额：<font color='red'><b>"+ div100(pageObj.sumResult.sysAmtFeeSum)+"</b></font>  元</span>";
         paginationTable(pageObj,"resultList",cellFuncs,str,"queryMerHlog");
     };
     
 //商户请求后台
   function sendRequests(tseq){
//		useLoadingImage("../../public/images/wbox/loading.gif");
//		QueryMerMerHlogDetailService.notifyMerBkUrl(tseq,t,function(msg){alert(msg);});
	   
	   	$("v_mid").innerHTML = query_cash[tseq].mid;
		$("v_org_oid").innerHTML = query_cash[tseq].oid;
		$("v_mdate").innerHTML = query_cash[tseq].mdate;
		$("v_tseq").innerHTML = query_cash[tseq].tseq;
		$("v_amount").innerHTML = div100(query_cash[tseq].amount);
		$("v_feeAmt").innerHTML = div100(query_cash[tseq].feeAmt);
		$("v_type").innerHTML = h_type[query_cash[tseq].type];
		$("v_tstat").innerHTML = h_type[query_cash[tseq].tstat];
		$("v_sys_date").innerHTML = formatDate1(query_cash[tseq].sysDate11);
		$("v_bankStat").innerHTML = "初始化（未对账）";
		$("v_bk_seq1").innerHTML = query_cash[tseq].bk_seq1;
		$("v_bk_seq2").innerHTML = query_cash[tseq].bk_seq2;
		
		jQuery("#detail4One").wBox({title:"&nbsp;&nbsp;交易详情",show:true});//显示box
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
	 	QueryMerMerHlogDetailService.batchNotifyMerBkUrl(tseqs,'hlog',function(msg){
			alert(msg);
			queryMerHlog(1);
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