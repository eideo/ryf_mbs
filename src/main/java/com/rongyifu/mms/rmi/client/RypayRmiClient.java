package com.rongyifu.mms.rmi.client;

import java.util.Map;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.utils.LogUtil;

public class RypayRmiClient{
	
	private RypayRmiClient(){
	}
	
	private static HttpInvokerProxyFactoryBean proxyFactory = null;
	
	private static final String RMI_METHOD_NAME = "invokeMethod";
	
	static {
		init();
	}
	
	private static void init(){
		LogUtil.printInfoLog("== RypayRmiClient initialization start ==");
		String serviceUrl = ParamCache.getStrParamByName("MMS_RMI_URL");
		LogUtil.printInfoLog("serviceUrl=" + serviceUrl);
		proxyFactory = RmiClient.init(serviceUrl, IRemoteService.class);
		LogUtil.printInfoLog("== RypayRmiClient initialization end ==");
	}
	
	private synchronized static void checkInit(){
		if(proxyFactory == null)
			init();
	}
	
	public static Object executeRequest(Map<String, Object> argsMap) throws Exception{
		checkInit();
		return RmiClient.executeRequest(proxyFactory, argsMap, RMI_METHOD_NAME);
	}
}
