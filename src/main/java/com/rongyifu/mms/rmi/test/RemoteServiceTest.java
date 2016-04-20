package com.rongyifu.mms.rmi.test;

import java.util.HashMap;
import java.util.Map;

import com.rongyifu.mms.rmi.client.RypayRmiClient;

public class RemoteServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> argsMap = new HashMap<String, Object>();
		argsMap.put("serviceName", "rmi_test");
		argsMap.put("a", "a12");
		argsMap.put("b", 10);
		argsMap.put("c", "测试1");
		
		try {
			String result = (String) RypayRmiClient.executeRequest(argsMap);
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
