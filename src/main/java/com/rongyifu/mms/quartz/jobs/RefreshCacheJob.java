package com.rongyifu.mms.quartz.jobs;

import com.rongyifu.mms.utils.RYFMapUtil;

public class RefreshCacheJob {

	public void refresh() {
		RYFMapUtil mapUtil = RYFMapUtil.getInstance();
		// 刷新商户信息表
		mapUtil.refreshMinfoMap();
		// 刷新商户手续费公式表
		mapUtil.refreshFeeCalcModel();
		// 刷新网关信息
		mapUtil.refreshGateList();
	}
}
