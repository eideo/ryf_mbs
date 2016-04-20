var mid, operId,  bdate,  edate;
function judgeCondition(){
    mid = $("mid").value;
    operId = $("operId").value;
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

  function getMidOperLog(pageNo) {

			 if(!judgeCondition())return;
				MerMerchantService.getMidOperLog(mid,operId,bdate, edate,pageNo,callBack2);

 }    
  
  var callBack2 = function(pageObj){
	   $("merLogTable").style.display="";
	   if(pageObj==null)document.getElementById("resultList").appendChild(creatNoRecordTr(9));
	   var cellFuncs = [
	                    function(obj) { return obj.mid; },
	                    function(obj) { return obj.name; },
	                    function(obj) { return obj.operId; },
	                    function(obj) { return obj.oper_name; },
	                    function(obj) { return obj.sysDate; },
	                    function(obj) { return getStringTime(obj.sysTime); },
	                    function(obj) { return obj.operIp; },
	                    function(obj) { return obj.action; },
	                    function(obj) { return obj.actionDesc; }
	                 
	                ];	  
        
        paginationTable(pageObj,"resultList",cellFuncs,"","getMidOperLog");
    };
    
    
    //商户明细查询下载
    function downloadLog(){
 	    if(!judgeCondition())return;
 	    dwr.engine.setAsync(false);//把ajax调用设置为同步
 	   MerMerchantService.downloadLog(mid,operId,bdate, edate,
 	    				function(data){dwr.engine.openInDownload(data);});
 	}