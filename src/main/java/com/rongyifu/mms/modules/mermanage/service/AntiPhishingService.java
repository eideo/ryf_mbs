package com.rongyifu.mms.modules.mermanage.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.rongyifu.mms.bean.LoginUser;
import com.rongyifu.mms.modules.mermanage.dao.AntiPhishingDao;
import com.rongyifu.mms.rmi.client.RypayRmiClient;
import com.rongyifu.mms.utils.LogUtil;
import com.rongyifu.mms.web.WebConstants;

/**
 * 防伪信息设置 
 *
 */
public class AntiPhishingService {
	
	public String doSave(String antiPhishingStr){
		HttpSession session = WebContextFactory.get().getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(WebConstants.SESSION_LOGGED_ON_USER);
		String mid = loginUser.getMid();
		Integer operId = loginUser.getOperId();
		String returnMsg = null;
		
		Map<String, Object> reqPramas = new HashMap<String, Object>();
		reqPramas.put("serviceName", "rmi_save_Ant");
		reqPramas.put("mid", mid);
		reqPramas.put("operId", operId);
		reqPramas.put("antiPhishingStr", antiPhishingStr);
		try {
			returnMsg=(String)RypayRmiClient.executeRequest(reqPramas);
			// 更新session中的防伪信息
			loginUser.setAntiPhishingStr(antiPhishingStr);
			// 记录日志
			Map<String, Object> reqPramas1 = new HashMap<String, Object>();
			String logInfo = "[商户号： "+mid+" 操作员： "+operId+" 防伪信息："+antiPhishingStr + "]";
			reqPramas1.put("serviceName", "rmi_save_log");
			reqPramas1.put("action", "防伪信息设置");
			reqPramas1.put("actionDesc", logInfo);
			reqPramas1.put("mid", mid);
			reqPramas1.put("operId", operId);
			
			WebContext webContext = WebContextFactory.get();
			HttpServletRequest request = null;
			request = webContext.getHttpServletRequest();
			reqPramas1.put("operIp", request.getRemoteHost());
			RypayRmiClient.executeRequest(reqPramas1);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.printErrorLog("doSave", e);
		}
		
		return returnMsg;
	}
}
