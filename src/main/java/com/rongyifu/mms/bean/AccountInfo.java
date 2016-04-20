package com.rongyifu.mms.bean;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.utils.DateUtil;
//账户信息查询
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	   //商户号
	private String mid;
	// 版本号
	private Integer version;
	//基本信息：
	//账户号
	private Integer accId;
	//姓名/企业名称
	private String comName;
	//账户类型
	private Short accType;
	//商户名
	private String merName;
	//手机号码
	private String phone;
	//邮箱
	private String email;
	//账户状态
	private Short accState;
	//证件类型
	private Short certType;
	//证件号码
	private String certNo;
	//证件认证状态
	private Short authType;
	//注册时间
	private String regTime;
	//银行卡信息:
	//银行卡类型
	private Short cardType;
	//开户银行名
	private String bankName;
	//卡号
	private Integer cardNo;
	//预留手机号码
	private Integer bankPhone;
	//卡认证状态
	private Short cardAuthStatus;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getAccId() {
		return accId;
	}
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public Short getAccType() {
		return accType;
	}
	public void setAccType(Short accType) {
		this.accType = accType;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Short getAccState() {
		return accState;
	}
	public void setAccState(Short accState) {
		this.accState = accState;
	}
	public Short getCertType() {
		return certType;
	}
	public void setCertType(Short certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public Short getAuthType() {
		return authType;
	}
	public void setAuthType(Short authType) {
		this.authType = authType;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public Short getCardType() {
		return cardType;
	}
	public void setCardType(Short cardType) {
		this.cardType = cardType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getCardNo() {
		return cardNo;
	}
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getBankPhone() {
		return bankPhone;
	}
	public void setBankPhone(Integer bankPhone) {
		this.bankPhone = bankPhone;
	}
	public Short getCardAuthStatus() {
		return cardAuthStatus;
	}
	public void setCardAuthStatus(Short cardAuthStatus) {
		this.cardAuthStatus = cardAuthStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}