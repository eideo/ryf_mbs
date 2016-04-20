package com.rongyifu.mms.quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.utils.LogUtil;

public class QuartzAutoStartListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		QuartzUtils.LOCK.lock();
		try {
			if(Ryt.isStartService("QuartzAutoStart check.")){
				LogUtil.printInfoLog("need to start QuartzUtils.");
				QuartzUtils.start();
			}else{
				LogUtil.printInfoLog("need not to start QuartzUtils.");
			}
		} catch (Exception e) {
			QuartzUtils.shutDown();
		}finally{
			QuartzUtils.startChecker();//启动定时任务启动检查线程
			QuartzUtils.LOCK.unlock();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
		try {
			QuartzUtils.shutDown();
		} catch (Exception e) {
			LogUtil.printErrorLog("", e);
		}
	}
}
