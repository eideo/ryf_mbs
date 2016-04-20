
//账户余额查询
function initZHYECX(){
	queryZHYE();
}

function queryZHYE(){
	var mid=$("mid").value;
	MerZHService.getAccBalance(mid,function callback(myList){
		
			 dwr.util.removeAllRows("resultList");
			return ;
		var cellFuns=[
						function(obj){ return obj.accId1;},
						function(obj){ return obj.accId1;},
						function(obj){ return div100(obj.balance);}
						
						];
		dwr.util.removeAllRows("resultList");
	    dwr.util.addRows("resultList",myList,cellFuns,{escapeHtml:false});
		}
	);
}