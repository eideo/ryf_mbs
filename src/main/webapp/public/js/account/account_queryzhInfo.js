 //账户类型
var accTypeMap={B:"企业级账户",C:"个人级账户"};
//账户状态
var accStateMap={I:"未激活",N:"正常",C:"被关闭",D:"销户"};
//证件类型 
var certTypeMap={01:"身份证",02:"护照",10:"营业执照号"}; 
//证件认证状态--验证中,主要针对商户证件审核
var authTypeMap={U:"未验证",F:"验证失败",P:"验证中",S:"验证成功"}; 
//卡类型
var cardTypeMap={C:"信用卡",D:"借记卡",U:"未知卡"}; 
//卡状态-N:"正常，该记录有效,‘C’ – 关闭，该记录无效
var cardAuthStatusMap={N:"验证成功",C:"验证失败"};

function init(mid){
       queryAccountInfo(mid);
    }
  
  //查询账户信息
  function queryAccountInfo(mid) {
	  MerAccountService.getaccountInfo(mid, function(accountInfo) {
		//基本信息：
			dwr.util.setValues( {
	    	//账户号
				v_accId : accountInfo.accId,
	    	//姓名/企业名称
				v_comName : accountInfo.comName,
	    	//账户类型
				v_accType : accTypeMap[accountInfo.accType],
	    	//商户名
				v_merName : accountInfo.merName,
	    	//手机号码
				v_phone : accountInfo.phone,
	    	//邮箱
				v_email : accountInfo.email,
	    	//账户状态
				v_accState : accStateMap[accountInfo.accState],
	    	//证件类型
				v_certType : certTypeMap[accountInfo.certType],
	    	//证件号码
				v_certNo : accountInfo.certNo,
	    	//证件认证状态
				v_authType : authTypeMap[accountInfo.authType],
	    	//注册时间
				v_regTime : accountInfo.regTime
			 });    
			
	    	//银行卡信息:
	         dwr.util.setValues( {
	        	//银行卡类型
	             v_cardType : cardTypeMap[accountInfo.cardType],
	 	    	//开户银行名
	             v_bankName : accountInfo.bankName,
	 	    	//卡号
	             v_cardNo : accountInfo.cardNo,
	 	    	//预留手机号码
	             v_bankPhone : accountInfo.bankPhone,
	 	    	//卡认证状态
	             v_cardAuthStatus : cardAuthStatusMap[accountInfo.cardAuthStatus]
	         });         
     });
 }             