package com.rongyifu.mms.rmi.client;

import java.util.Map;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

public class RmiClient {
	
	public static HttpInvokerProxyFactoryBean init(String serviceUrl, Class<?> serviceInterface){
		HttpInvokerProxyFactoryBean proxyFactory = new HttpInvokerProxyFactoryBean();
		proxyFactory.setServiceUrl(serviceUrl);
        proxyFactory.setServiceInterface(serviceInterface);
        proxyFactory.afterPropertiesSet();
        return proxyFactory;
	}
	
	public static Object executeRequest(HttpInvokerProxyFactoryBean proxyFactory, Object[] arguments, String methodName, Class<?>[] parameterTypes) throws Exception{
		RemoteInvocation remoteInvocation = new RemoteInvocation();
        remoteInvocation.setArguments(arguments);
        remoteInvocation.setMethodName(methodName);
        remoteInvocation.setParameterTypes(parameterTypes);
		
		HttpInvokerRequestExecutor httpExecutor = proxyFactory.getHttpInvokerRequestExecutor();
		RemoteInvocationResult result = httpExecutor.executeRequest(proxyFactory, remoteInvocation);
		return result.getValue();
	}
	
	public static Object executeRequest(HttpInvokerProxyFactoryBean proxyFactory, Map<String, Object> argsMap, String methodName) throws Exception{
		Object[] arguments = new Object[]{argsMap};
		Class<?>[] parameterTypes = new Class<?>[]{Map.class};
		return executeRequest(proxyFactory, arguments, methodName, parameterTypes);
	}
}
