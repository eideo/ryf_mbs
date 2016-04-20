package com.rongyifu.mms.modules.ftp.dao;

import com.rongyifu.mms.bean.FtpDownload;
import com.rongyifu.mms.bean.MRyfFtp;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.db.PubDao;
import com.rongyifu.mms.utils.CurrentPage;
@SuppressWarnings("rawtypes")
public class FtpDao extends PubDao{

	public CurrentPage<FtpDownload> queryDownloads(Integer bdate,Integer edate,int operId,short ftpSource ,String mid,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		
		//sql.append("select * from ftp_download fd where fd.id > 0");
		
		StringBuffer condition = new StringBuffer();
		condition.append(" and fd.mid = " + Ryt.addQuotes(mid));
		condition.append(" and fd.oper_id = " + operId);
		if (bdate !=null){
			condition.append(" and SUBSTRING(fd.sys_date,1,8) >= " + bdate);
		}
		if (edate !=null){
			condition.append(" and SUBSTRING(fd.sys_date,1,8) <= " + edate);
		}
		if(ftpSource>0){
			condition.append(" and fd.ftp_source = " + ftpSource);
		}	
		String sql= "select * from ftp_download fd where fd.id > 0 "+condition.toString() + " ORDER BY fd.sys_date DESC";
		String sqlCount="select count(*) from ftp_download fd where fd.id > 0" + condition.toString();
		return queryForPage(sqlCount,sql.toString(), pageNo,pageSize,FtpDownload.class);
		 
	}
	
	public int insertFtpDown(FtpDownload ftpDownload){
		StringBuffer sql=new StringBuffer();
		sql.append("insert into ftp_download (ftp_name,ftp_url,ftp_source,sys_date,oper_id,mid) ");
		sql.append(" values(").append(Ryt.addQuotes(ftpDownload.getFtpName())).append(",").append(Ryt.addQuotes(ftpDownload.getFtpUrl()));
		sql.append(",").append(ftpDownload.getFtpSource()).append(",").append(ftpDownload.getSysDate());
		sql.append(",").append(ftpDownload.getOperId()).append(",");
		sql.append(Ryt.addQuotes(ftpDownload.getMid())).append(")");
		return update(sql.toString());
		
	}
	public MRyfFtp getRyfFtpById(String id){
		String sql = "SELECT * from m_ryf_ftp where id="+Ryt.addQuotes(id);
		return queryForObject(sql, MRyfFtp.class);
	}

}
