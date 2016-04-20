package com.rongyifu.mms.modules.ftp.service;


import org.directwebremoting.io.FileTransfer;

import com.rongyifu.mms.bean.FtpDownload;
import com.rongyifu.mms.bean.MRyfFtp;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.modules.ftp.dao.FtpDao;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.FtpUtil.Ftp;

public class FtpService {
	private FtpDao dao = new FtpDao();
	
	public CurrentPage<FtpDownload> queryDownloads(Integer bdate,Integer edate,int operId,short ftpSource ,String mid,int pageNo){
		return dao.queryDownloads(bdate,edate,operId,ftpSource,mid,pageNo,AppParam.getPageSize());
	
	}
	/**
	 * 对已上传的文件下载
	 * @param fileName  文件名
	 * @param downFileName   下载的文件上一级路径
	 * @return
	 * @throws Exception
	 */
	public FileTransfer downloadFile(String downFileName,String fileName){
		try {
			MRyfFtp ryfFtp = dao.getRyfFtpById("MINFO_DOWN");
			Ftp ftp=new Ftp(ryfFtp.getFtpIp(),ryfFtp.getFtpPort(),ryfFtp.getFtpName(),ryfFtp.getFtpPsw());  
	        ftp.ftpLogin();
			return ftp.downloadFile(downFileName,fileName, ftp);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		return null;
	}
	
	
	public int insertFtpDown(FtpDownload ftpDownload){
		return dao.insertFtpDown(ftpDownload);
	}

}
