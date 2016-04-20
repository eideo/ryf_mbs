package com.rongyifu.mms.rmi.client;

import java.util.Map;

public interface IRemoteService {
	
	public Object invokeMethod(Map<String, Object> args);
}
