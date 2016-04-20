package com.rongyifu.mms.bean;


import org.directwebremoting.annotations.DataTransferObject;
import com.rongyifu.mms.utils.ErrorCodes;

@DataTransferObject
public class TradeInfo {
	private static final long serialVersionUID = -7983269728932829511L;
	private String tseq;
	private Integer version;
	private Long ip;
	private Integer mdate;
	private String mid;
	private String  bid;
	private String oid;
	private Long amount;
	private Short type;
	private Integer gate;
	private Long sysDate;
	private Integer initSysDate;
	private Integer sysTime;
	private Long batch;
	private Integer feeAmt;
	private Integer bankFee;
	private String tstat;
	private Integer bkFlag;
	private Long orgSeq;
	private Long refSeq;
	private Integer refundAmt;
	private String merPriv;
	private Integer bkSend;
	private Integer bkRecv;
	private String bkUrl;
	private String fgUrl;
	private Short bkChk;
	private Integer bkDate;
	private String bk_seq1;
	private String bk_seq2;
	private String bkResp;
	private String mobileNo;
	private Integer transPeriod;
	private String cardNo;
	private String errorCode;
	private String phoneNo;
	private Integer authorType;
	private Integer operId;
	private Integer merTradeType;
	private Integer gid;
	private Integer  terminalType;
	private String  terminalNumber;
	private Integer  cid;
	private Integer channelId;
	
	private String  xpepMercode;
	private String  xpepTermId;
	private String  xpepTrancode;
	private String  xpepTrace;
	private Long  xpepTradeDatetime;
	private String  xpepOutcard;
	private String  xpepTradeResp;
	private Integer  xpeTradetype;
	private String  xpeDeductMercode;
	private String  xpeDeductTermid;
	private String  xpeDeductTrace;
	private Integer  xpeDeductInsid;
	private String  xpeDeductResp;
	private String  xpeDeductRefer;
	private String  xpeDeductAuthcode;
	private Boolean  xpeDeductRollbkFlag;
	private String  xpeDeductRollbkReason;
	private String  xpeDeductRollbkStance;
	private String  xpeDeductRollbkResp;
	private Integer xpepTrademsgType;
	private String tradeCode;
	private String unionpayMerId;
	private String unionpayTermId;
	private Integer gateRouteId;
	private Integer termType;
	private String innerTermId;
	private String xpepIntcdNo;
	
	private Integer xpepGainSysId; //入账机构号
	private String xpepGainSysStance; //入账流水
	private String xpepGainMerCode; //入账商户号
	private String xpepGainMerTermId; //入账终端号
	private Long xpepGainTradeAmount; //入账金额
	private String xpepGainSysResponse; //入账应答码
	private String xpepGainSysReference; //入账参考号
	private Integer xpepGainResult; //入账结果
	private String xpepAuthCode; //授权码
	private String bkFeeModel;  //银行手续费公式
	private String bankFeeModel; //结算后银行手续费
	private String xpeDeductRollBkResponse; //冲正返回码
	
	private String termChannelId; //商户渠道号
	private String termChannelName; //商户渠道名称
	private Integer flag;//数据是否同步
	private String xpepSendcde;
	private String xpepRYFState;
	private String abbrev;
	private String abbrevName;
	private String xpepPosmercode;
	private String xpeDeductRollBkSysReference; //冲正参考号
	
	private String xpepPoitcde;
	private String gisaddr;
	private String installAddress;
	private String picPath;
	private String geoAddress;
	private String tradeName;//交易类型描述
	private String merFeeModel; //系统手续费
	
	public String getXpepRYFState() {
		return xpepRYFState;
	}
	public void setXpepRYFState(String xpepRYFState) {
		this.xpepRYFState = xpepRYFState;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getOperId() {
		return operId;
	}
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	public Integer getAuthorType() {
		return authorType;
	}
	public void setAuthorType(Integer authorType) {
		this.authorType = authorType;
	}
	public TradeInfo() {
		super();
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getBankFee() {
		return bankFee;
	}
	public void setBankFee(Integer bankFee) {
		this.bankFee = bankFee;
	}
	public Long getBatch() {
		return batch;
	}
	public void setBatch(Long batch) {
		this.batch = batch;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public Short getBkChk() {
		return bkChk;
	}
	public void setBkChk(Short bkChk) {
		this.bkChk = bkChk;
	}
	public Integer getBkDate() {
		return bkDate;
	}
	public void setBkDate(Integer bkDate) {
		this.bkDate = bkDate;
	}
	public Integer getBkFlag() {
		return bkFlag;
	}
	public void setBkFlag(Integer bkFlag) {
		this.bkFlag = bkFlag;
	}
	public Integer getBkRecv() {
		return bkRecv;
	}
	public void setBkRecv(Integer bkRecv) {
		this.bkRecv = bkRecv;
	}
	public String getBkResp() {
		return bkResp;
	}
	public void setBkResp(String bkResp) {
		this.bkResp = bkResp== null? "" : bkResp;
	}
	public Integer getBkSend() {
		return bkSend;
	}
	public void setBkSend(Integer bkSend) {
		this.bkSend = bkSend;
	}
    
	public String getBk_seq1() {
		return bk_seq1;
	}
	public void setBk_seq1(String bk_seq1) {
		this.bk_seq1 = bk_seq1 == null ? "" : bk_seq1;
	}
	public String getBk_seq2() {
		return bk_seq2==null ? "" : bk_seq2;
	}
	public void setBk_seq2(String bk_seq2) {
		this.bk_seq2 = bk_seq2;
	}
	public String getBkUrl() {
		return bkUrl;
	}
	public void setBkUrl(String bkUrl) {
		this.bkUrl = bkUrl;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getErrorCode() {//返回错误代码  pnr汇付，19pay（高阳）
		if(authorType==null || errorCode ==null){ 
			return "";
		}
		if(1 == authorType){
			String msg = ErrorCodes.PNR_CODE.get(errorCode);
			return msg==null ? "" : msg;
		}else if(3 == authorType){
			String msg2 = ErrorCodes.I19Pay_CODE.get(errorCode);
			return (msg2== null ? "" : msg2 );
		}else if(4 == authorType){
			String msg4 = ErrorCodes.KQ_CODE.get(errorCode);
			return (msg4== null ? "" : msg4 );
		}else{
			return "";
		}
	}
	public void setErrorCode(String errorCode) {
		this.errorCode=errorCode;
	}
	public Integer getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(Integer feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getFgUrl() {
		return fgUrl;
	}
	public void setFgUrl(String fgUrl) {
		this.fgUrl = fgUrl;
	}
	public Integer getGate() {
		return gate;
	}
	public void setGate(Integer gate) {
		this.gate = gate;
	}
/*	public String getGate_desc_short() {
		return gate_desc_short;
	}
	public void setGate_desc_short(String gate_desc_short) {
		this.gate_desc_short = gate_desc_short;
	}*/
	public Integer getInitSysDate() {
		return initSysDate;
	}
	public void setInitSysDate(Integer initSysDate) {
		this.initSysDate = initSysDate;
	}
	public Long getIp() {
		return ip;
	}
	public void setIp(Long ip) {
		this.ip = ip;
	}
	public Integer getMdate() {
		return mdate;
	}
	public void setMdate(Integer mdate) {
		this.mdate = mdate;
	}
	public String getMerPriv() {
		return merPriv == null ? "" : merPriv;
	}
	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
/*	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}*/
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Long getOrgSeq() {
		return orgSeq;
	}
	public void setOrgSeq(Long orgSeq) {
		this.orgSeq = orgSeq;
	}
	public Long getRefSeq() {
		return refSeq;
	}
	public void setRefSeq(Long refSeq) {
		this.refSeq = refSeq;
	}
	public Integer getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(Integer refundAmt) {
		this.refundAmt = refundAmt;
	}
	public Long getSysDate() {
		return sysDate;
	}
	public void setSysDate(Long sysDate) {
		this.sysDate = sysDate;
	}
	public Integer getSysTime() {
		return sysTime;
	}
	public void setSysTime(Integer sysTime) {
		this.sysTime = sysTime;
	}
	public Integer getTransPeriod() {
		return transPeriod;
	}
	public void setTransPeriod(Integer transPeriod) {
		this.transPeriod = transPeriod;
	}
	public String getTseq() {
		return tseq;
	}
	public void setTseq(String tseq) {
		this.tseq = tseq;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getTstat() {
		return tstat;
	}
	public void setTstat(String tstat) {
		this.tstat = tstat;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getMerTradeType() {
		return merTradeType;
	}
	public void setMerTradeType(Integer merTradeType) {
		this.merTradeType = merTradeType;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public String getTerminalNumber() {
		return terminalNumber;
	}
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getXpepMercode() {
		return xpepMercode;
	}
	public void setXpepMercode(String xpepMercode) {
		this.xpepMercode = xpepMercode;
	}
	public String getXpepTermId() {
		return xpepTermId;
	}
	public void setXpepTermId(String xpepTermId) {
		this.xpepTermId = xpepTermId;
	}
	public String getXpepTrancode() {
		return xpepTrancode;
	}
	public void setXpepTrancode(String xpepTrancode) {
		this.xpepTrancode = xpepTrancode;
	}
	public String getXpepTrace() {
		return xpepTrace;
	}
	public void setXpepTrace(String xpepTrace) {
		this.xpepTrace = xpepTrace;
	}

	public Long getXpepTradeDatetime() {
		return xpepTradeDatetime;
	}
	public void setXpepTradeDatetime(Long xpepTradeDatetime) {
		this.xpepTradeDatetime = xpepTradeDatetime;
	}
	public String getXpepOutcard() {
		return xpepOutcard;
	}
	public void setXpepOutcard(String xpepOutcard) {
		this.xpepOutcard = xpepOutcard;
	}
	public String getXpepTradeResp() {
		return xpepTradeResp;
	}
	public void setXpepTradeResp(String xpepTradeResp) {
		this.xpepTradeResp = xpepTradeResp;
	}
	public Integer getXpeTradetype() {
		return xpeTradetype;
	}
	public void setXpeTradetype(Integer xpeTradetype) {
		this.xpeTradetype = xpeTradetype;
	}
	public String getXpeDeductMercode() {
		return xpeDeductMercode;
	}
	public void setXpeDeductMercode(String xpeDeductMercode) {
		this.xpeDeductMercode = xpeDeductMercode;
	}
	public String getXpeDeductTermid() {
		return xpeDeductTermid;
	}
	public void setXpeDeductTermid(String xpeDeductTermid) {
		this.xpeDeductTermid = xpeDeductTermid;
	}
	public String getXpeDeductTrace() {
		return xpeDeductTrace;
	}
	public void setXpeDeductTrace(String xpeDeductTrace) {
		this.xpeDeductTrace = xpeDeductTrace;
	}
	public Integer getXpeDeductInsid() {
		return xpeDeductInsid;
	}
	public void setXpeDeductInsid(Integer xpeDeductInsid) {
		this.xpeDeductInsid = xpeDeductInsid;
	}
	public String getXpeDeductResp() {
		return xpeDeductResp;
	}
	public void setXpeDeductResp(String xpeDeductResp) {
		this.xpeDeductResp = xpeDeductResp;
	}
	public String getXpeDeductRefer() {
		return xpeDeductRefer;
	}
	public void setXpeDeductRefer(String xpeDeductRefer) {
		this.xpeDeductRefer = xpeDeductRefer;
	}
	public String getXpeDeductAuthcode() {
		return xpeDeductAuthcode;
	}
	public void setXpeDeductAuthcode(String xpeDeductAuthcode) {
		this.xpeDeductAuthcode = xpeDeductAuthcode;
	}
	public Boolean getXpeDeductRollbkFlag() {
		return xpeDeductRollbkFlag;
	}
	public void setXpeDeductRollbkFlag(Boolean xpeDeductRollbkFlag) {
		this.xpeDeductRollbkFlag = xpeDeductRollbkFlag;
	}
	public String getXpeDeductRollbkReason() {
		return xpeDeductRollbkReason;
	}
	public void setXpeDeductRollbkReason(String xpeDeductRollbkReason) {
		this.xpeDeductRollbkReason = xpeDeductRollbkReason;
	}
	public String getXpeDeductRollbkStance() {
		return xpeDeductRollbkStance;
	}
	public void setXpeDeductRollbkStance(String xpeDeductRollbkStance) {
		this.xpeDeductRollbkStance = xpeDeductRollbkStance;
	}
	public String getXpeDeductRollbkResp() {
		return xpeDeductRollbkResp;
	}
	public void setXpeDeductRollbkResp(String xpeDeductRollbkResp) {
		this.xpeDeductRollbkResp = xpeDeductRollbkResp;
	}
	public Integer getXpepTrademsgType() {
		return xpepTrademsgType;
	}
	public void setXpepTrademsgType(Integer xpepTrademsgType) {
		this.xpepTrademsgType = xpepTrademsgType;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getUnionpayMerId() {
		return unionpayMerId;
	}
	public void setUnionpayMerId(String unionpayMerId) {
		this.unionpayMerId = unionpayMerId;
	}
	public String getUnionpayTermId() {
		return unionpayTermId;
	}
	public void setUnionpayTermId(String unionpayTermId) {
		this.unionpayTermId = unionpayTermId;
	}
	public Integer getGateRouteId() {
		return gateRouteId;
	}
	public void setGateRouteId(Integer gateRouteId) {
		this.gateRouteId = gateRouteId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getTermType() {
		return termType;
	}
	public void setTermType(Integer termType) {
		this.termType = termType;
	}
	public String getInnerTermId() {
		return innerTermId;
	}
	public void setInnerTermId(String innerTermId) {
		this.innerTermId = innerTermId;
	}
	public String getXpepIntcdNo() {
		return xpepIntcdNo;
	}
	public void setXpepIntcdNo(String xpepIntcdNo) {
		this.xpepIntcdNo = xpepIntcdNo;
	}
	public Integer getXpepGainSysId() {
		return xpepGainSysId;
	}
	public void setXpepGainSysId(Integer xpepGainSysId) {
		this.xpepGainSysId = xpepGainSysId;
	}
	public String getXpepGainSysStance() {
		return xpepGainSysStance;
	}
	public void setXpepGainSysStance(String xpepGainSysStance) {
		this.xpepGainSysStance = xpepGainSysStance;
	}
	public String getXpepGainMerCode() {
		return xpepGainMerCode;
	}
	public void setXpepGainMerCode(String xpepGainMerCode) {
		this.xpepGainMerCode = xpepGainMerCode;
	}
	public String getXpepGainMerTermId() {
		return xpepGainMerTermId;
	}
	public void setXpepGainMerTermId(String xpepGainMerTermId) {
		this.xpepGainMerTermId = xpepGainMerTermId;
	}
	public Long getXpepGainTradeAmount() {
		return xpepGainTradeAmount;
	}
	public void setXpepGainTradeAmount(Long xpepGainTradeAmount) {
		this.xpepGainTradeAmount = xpepGainTradeAmount;
	}
	public String getXpepGainSysResponse() {
		return xpepGainSysResponse;
	}
	public void setXpepGainSysResponse(String xpepGainSysResponse) {
		this.xpepGainSysResponse = xpepGainSysResponse;
	}
	public String getXpepGainSysReference() {
		return xpepGainSysReference;
	}
	public void setXpepGainSysReference(String xpepGainSysReference) {
		this.xpepGainSysReference = xpepGainSysReference;
	}
	public Integer getXpepGainResult() {
		return xpepGainResult;
	}
	public void setXpepGainResult(Integer xpepGainResult) {
		this.xpepGainResult = xpepGainResult;
	}
	public String getXpepAuthCode() {
		return xpepAuthCode;
	}
	public void setXpepAuthCode(String xpepAuthCode) {
		this.xpepAuthCode = xpepAuthCode;
	}
	public String getBkFeeModel() {
		return bkFeeModel;
	}
	public void setBkFeeModel(String bkFeeModel) {
		this.bkFeeModel = bkFeeModel;
	}
	public String getBankFeeModel() {
		return bankFeeModel;
	}
	public void setBankFeeModel(String bankFeeModel) {
		this.bankFeeModel = bankFeeModel;
	}
	public String getXpeDeductRollBkResponse() {
		return xpeDeductRollBkResponse;
	}
	public void setXpeDeductRollBkResponse(String xpeDeductRollBkResponse) {
		this.xpeDeductRollBkResponse = xpeDeductRollBkResponse;
	}
	public String getTermChannelId() {
		return termChannelId;
	}
	public void setTermChannelId(String termChannelId) {
		this.termChannelId = termChannelId;
	}
	public String getTermChannelName() {
		return termChannelName;
	}
	public void setTermChannelName(String termChannelName) {
		this.termChannelName = termChannelName;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getXpepSendcde() {
		return xpepSendcde;
	}
	public void setXpepSendcde(String xpepSendcde) {
		this.xpepSendcde = xpepSendcde;
	}
	public String getAbbrev() {
		return abbrev;
	}
	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}
	public String getAbbrevName() {
		return abbrevName;
	}
	public void setAbbrevName(String abbrevName) {
		this.abbrevName = abbrevName;
	}
	public String getXpepPosmercode() {
		return xpepPosmercode;
	}
	public void setXpepPosmercode(String xpepPosmercode) {
		this.xpepPosmercode = xpepPosmercode;
	}
	public String getXpeDeductRollBkSysReference() {
		return xpeDeductRollBkSysReference;
	}
	public void setXpeDeductRollBkSysReference(String xpeDeductRollBkSysReference) {
		this.xpeDeductRollBkSysReference = xpeDeductRollBkSysReference;
	}
	public String getXpepPoitcde() {
		return xpepPoitcde;
	}
	public void setXpepPoitcde(String xpepPoitcde) {
		this.xpepPoitcde = xpepPoitcde;
	}
	public String getGisaddr() {
		return gisaddr;
	}
	public void setGisaddr(String gisaddr) {
		this.gisaddr = gisaddr;
	}
	public String getInstallAddress() {
		return installAddress;
	}
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getGeoAddress() {
		return geoAddress;
	}
	public void setGeoAddress(String geoAddress) {
		this.geoAddress = geoAddress;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getMerFeeModel() {
		return merFeeModel;
	}
	public void setMerFeeModel(String merFeeModel) {
		this.merFeeModel = merFeeModel;
	}
	
}
