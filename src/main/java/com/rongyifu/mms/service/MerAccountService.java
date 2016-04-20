package com.rongyifu.mms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ibm.icu.text.SimpleDateFormat;
import com.rongyifu.mms.bean.AccInfos;
import com.rongyifu.mms.bean.AccountInfo;
import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.rmi.client.RypayRmiClient;
import com.rongyifu.mms.utils.DateUtil;
import com.rongyifu.mms.utils.LogUtil;
import com.rongyifu.mms.utils.MD5;

public class MerAccountService {
	
	/**
	 * 获得单个商户对象 -- 调用账户系统接口
	 * @param mid
	 * @return
	 */
	public List<AccountInfo> getaccountInfo(String mid) {
		LogUtil.printInfoLog("账户信息查询--调用账户系统接口");
		String url = null;
		Map<String, Object> maps = new HashMap<String, Object>();
        //消息版本号
		maps.put("version", 10);
		//交易码
		maps.put("tranCode", "ZH0061");
		//系统标识
		maps.put("sys", "RYF");
		//平台商户号
		maps.put("merId", mid);
		//时间戳  一分钟有效
		maps.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		//数字签名 参与数字签名的原字符串： version+tranCode+sys+merId+outUserId+timestamp
        maps.put("chkValue",MD5.getMD5(("10"+"ZH0061"+"RYF"+ mid  + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).getBytes()));
        //"10"+"ZH0061"+"RYF"+ mid  + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
		LogUtil.printInfoLog("账户信息查询--调用账户系统接口-参数",maps);
		
		try {
			String str = Ryt.requestWithPost(maps, url);
			if(str != null && str != ""){
				//解析数据
				JSONObject object = JSONObject.fromObject("str");
				String resCode = object.getString("resCode");
				
				if(resCode.equals("000")){
					List<AccountInfo> list = new ArrayList<AccountInfo>();
					AccountInfo account = new AccountInfo();
					String accId = object.getString("accId");   //账户号
					String comName = object.getString("comName"); //姓名/企业名称
					String accType = object.getString("accType");  //账户类型
			    	String merName = object.getString("merName");  //商户名
			    	String phone = object.getString("phone"); //手机号码
			    	String email = object.getString("email"); //邮箱
			    	String status = object.getString("status"); //账户状态
			    	String certType = object.getString("certType"); //证件类型
			    	String certNo = object.getString("certNo"); //证件号码
			    	String authType = object.getString("authType"); 	//证件认证状态
			    	String regTime = object.getString("regTime"); //注册时间
			    	String  cardType = object.getString("cardType"); 	//银行卡类型
			    	String bank = object.getString("bank"); //开户银行名
			    	String cardNo = object.getString("cardNo"); //卡号
			    	String bankPhone = object.getString("bankPhone");  //预留手机号码
			        String cardAuthStatus = object.getString("cardAuthStatus"); //卡认证状态
			        
			        account.setAccId(Integer.parseInt(accId));
			        account.setComName(comName);
			    	account.setAccType(Short.parseShort(accType));
			        account.setMerName(merName);
			    	account.setPhone(phone);
			        account.setEmail(email);
			        account.setAccState(Short.parseShort(status));
			        account.setCertType(Short.parseShort(certType));
			    	account.setCertNo(certNo);
			        account.setAuthType(Short.parseShort(authType));
			    	account.setRegTime(regTime);
			    	account.setCardType(Short.parseShort(cardType));
			    	account.setBankName(bank);
			    	account.setCardNo(Integer.parseInt(cardNo));
			    	account.setBankPhone(Integer.parseInt(bankPhone));
			    	account.setCardAuthStatus(Short.parseShort(cardAuthStatus));
			    	list.add(account);
			    	return list;
				}else{
					 String resMsg=object.getString("resMsg");
			    	 LogUtil.printInfoLog("失败原因"+resMsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.printInfoLog(e.getMessage());
}
		return null;
	}
	
}
