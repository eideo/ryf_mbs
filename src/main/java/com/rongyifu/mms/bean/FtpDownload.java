package com.rongyifu.mms.bean;

public class FtpDownload {
	private Integer id;
	private Integer operId;
	private String ftpName;
	private String ftpUrl;
	private Short ftpSource;
	private long sysDate;
	private Integer sysTime;
	private String ip;
	private Integer port;
	private String mid;
	
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getOperId() {
		return operId;
	}
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFtpName() {
		return ftpName;
	}
	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	public Short getFtpSource() {
		return ftpSource;
	}
	public void setFtpSource(Short ftpSource) {
		this.ftpSource = ftpSource;
	}
	
	
	public long getSysDate() {
		return sysDate;
	}
	public void setSysDate(long sysDate) {
		this.sysDate = sysDate;
	}
	public Integer getSysTime() {
		return sysTime;
	}
	public void setSysTime(Integer sysTime) {
		this.sysTime = sysTime;
	}
	
}
