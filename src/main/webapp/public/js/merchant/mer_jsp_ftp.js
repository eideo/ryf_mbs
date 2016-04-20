//交易数据下载<当天，历史交易数据>
//查询页面数据
 function init(){
            dwr.util.addOptions("ftp_source", ftp_source);
            
        }  
 var bdate,edate,ftpSource,operId,mid;
function judgeCondition(){
      
      operId = $("operId").value;
      mid = $("mid").value;
      ftpSource = $("ftp_source").value;
      bdate = $("statd").value;
      edate = $("endd").value;
      if (bdate == '') {
		alert("请选择系统起始日期！");
		return false;
	}
	if (edate == '') {
		alert("请选择系统结束日期！");
		return false;
	}
	return true;
}


//查询
function querylist(pageNo){
	 if(!judgeCondition())return;
	 FtpService.queryDownloads(bdate,edate,operId,ftpSource,mid,pageNo,callBack2);

}

var callBack2 = function(pageObj){
	$("tradelogTable").style.display = "";
	 dwr.util.removeAllRows("resultList");
	 if(pageObj==null){
  	   document.getElementById("resultList").appendChild(creatNoRecordTr(4));
		   return;
	   } 
	 var index = 1;
	var cellFuncs = [ 
	           function(obj) {
		            return index++;
               	},// 序号
               
               	function(obj) { return ftp_source[obj.ftpSource]; },
                function(obj) {
               		return "<a href=\"#\" onclick=downloadFile('"+obj.ftpUrl+"','"+ obj.ftpName +"'); class='box_detail'>"+obj.ftpName+"</a>"; 
               	},// 文件名
               
                function(obj) {
		            return formatDate1(obj.sysDate);
               	},//操作员名
	];
	paginationTable(pageObj,"resultList",cellFuncs,"","querylist");
};
//下载报表
function downloadFile(filePath,fileName){
	dwr.engine.setAsync(false);
	FtpService.downloadFile(filePath,fileName,function(data){
		dwr.engine.openInDownload(data);
	});
}
