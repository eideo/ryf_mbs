package com.rongyifu.mms.bean;

public class AccPayDetail {

	private String seqNo;
	private Integer transTime;
	private Integer flag;
	private Integer transAmt;
	private Integer fee;
	private Integer changeAmount;
	
	private Long balance;
	private Integer avaBalance;
	private Long frzBalance;
	private Integer type;
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public Integer getTransTime() {
		return transTime;
	}
	public void setTransTime(Integer transTime) {
		this.transTime = transTime;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(Integer transAmt) {
		this.transAmt = transAmt;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Integer getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(Integer changeAmount) {
		this.changeAmount = changeAmount;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Integer getAvaBalance() {
		return avaBalance;
	}
	public void setAvaBalance(Integer avaBalance) {
		this.avaBalance = avaBalance;
	}
	public Long getFrzBalance() {
		return frzBalance;
	}
	public void setFrzBalance(Long frzBalance) {
		this.frzBalance = frzBalance;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
