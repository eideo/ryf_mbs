package com.rongyifu.mms.api.bizobjs;

import java.util.Map;

import com.rongyifu.mms.api.BizObj;
import com.rongyifu.mms.bean.FtpDownload;
import com.rongyifu.mms.modules.ftp.service.FtpService;

public class FtpDownloadBizObj implements BizObj{

	@Override
	public Object doBiz(Map<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		FtpDownload down =new FtpDownload();
		down.setFtpName(params.get("filename"));
		down.setFtpSource(Short.parseShort(params.get("ftpSource")));
		down.setFtpUrl(params.get("Address"));
		down.setOperId(Integer.parseInt(params.get("operId")));
		down.setMid(params.get("mid"));
		down.setIp(params.get("ip"));
		down.setPort(Integer.parseInt(params.get("port")));
		down.setSysDate(Long.parseLong(params.get("uploadDate")));
		return new FtpService().insertFtpDown(down);
	}

}
