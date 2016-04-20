package com.rongyifu.mms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.directwebremoting.io.FileTransfer;

import com.ibm.icu.text.SimpleDateFormat;
import com.rongyifu.mms.bean.Account;
import com.rongyifu.mms.bean.AdjustAccount;
import com.rongyifu.mms.bean.FeeLiqBath;
import com.rongyifu.mms.bean.FeeLiqLog;
import com.rongyifu.mms.bean.Hlog;
import com.rongyifu.mms.bean.RefundLog;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.dao.BillListDownloadDao;
import com.rongyifu.mms.dao.SettlementDao;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.DateUtil;
import com.rongyifu.mms.utils.LogUtil;

public class MerSettlementService {
	private SettlementDao dao = new SettlementDao();
	
	/**
	 * 商户登录的对账单下载
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public FileTransfer downloadBillTXTData(Map<String, String> p) throws Exception {

		String content = "";
		int downDate = DateUtil.today();
		String mid = p.get("mid");
		String downType = p.get("downType");
		BillListDownloadDao billListDownloadDao = new BillListDownloadDao();

		if ("2".equals(p.get("tstat"))) {

			List<Hlog> hloglist = billListDownloadDao.queryPayBill(p);
			StringBuffer oidBuff = new StringBuffer();
			StringBuffer sheet = new StringBuffer();
			for (Hlog h : hloglist) {// 查询交易记录
				sheet.append(h.getMid() + "," + h.getOid() + ",");
				sheet.append(h.getMdate() + "," + Ryt.div100(h.getAmount()) + "," + Ryt.div100(h.getFeeAmt()) + ",");
				sheet.append(h.getTseq() + "," + h.getSysDate() + "," + h.getType() + ",");
				sheet.append(h.getTstat());
				sheet.append("\r\n");
				oidBuff.append(h.getOid() + "\r\n");
			}
			if (downType.equals("txt")) {// txt 下载

				// 商户号 商户名称 订单号 商户日期 交易类型 交易金额(元) 系统手续费(元) 交易状态 融易通流水号 网关号 系统日期
				String beginTitle = "TRADEDETAIL-START," + mid + "," + downDate + "," + hloglist.size() + ",S\r\n";
				String endTitle = "TRADEDETAIL-END";
				content = beginTitle + sheet.toString() + endTitle;

			}
		} else if ("3".equals(p.get("tstat"))) {
			List<RefundLog> refundLogList = billListDownloadDao.queryBackBill(p);
			// count += hloglist.size();
			StringBuffer oidBuff = new StringBuffer();
			StringBuffer sheet = new StringBuffer();
			for (RefundLog r : refundLogList) {// 查询记录
				// 商户号， 原商户订单号，原商户日期，退款金额，退回手续费，退款流水号，退款确认日期,退款经办日期，退款状态
				// mid,ref_amt,tseq,author_type,org_oid,gate,mdate,pro_date,stat
				
				sheet.append(r.getMid()).append(",");
				sheet.append(r.getOrg_oid()).append(",");
				//sheet.append(r.getMdate()).append(","); // 商户申请退款日期
				sheet.append(r.getOrg_mdate()).append(","); // 原商户交易日期
				sheet.append(Ryt.div100(r.getRef_amt())).append(",");
				sheet.append(Ryt.div100(r.getMerFee())).append(",");
				sheet.append(r.getId()).append(",");
				sheet.append(r.getReq_date()).append(",");
				sheet.append(r.getPro_date()).append(",");
				sheet.append(r.getStat()).append(",");
				sheet.append(r.getTseq());
				sheet.append("\r\n");
				oidBuff.append(r.getOrg_oid() + "\r\n");
			}
			if (downType.equals("txt")) {// txt 下载

				String beginTitle = "TRADEDETAIL-START," + mid + "," + downDate + "," + refundLogList.size() + ",S\r\n";
				String endTitle = "TRADEDETAIL-END";
				content = beginTitle + sheet.toString() + endTitle;

			}
		}
		String filename = "BILLTXT_" + DateUtil.today() + "." + downType;
		return new DownloadFile().downloadTXTFile(content, filename);

	}
	
	/**
	 * 用于结算单查询，查询fee_liq_bath表
	 * @param mid  商户号
	 * @param state  结算状态 1-已发起 2-已制表 3-已完成
	 * @param beginDate  查询起始日期
	 * @param endDate  查询结束日期
	 * @return
	 */
	public CurrentPage<FeeLiqBath> searchFeeLiqBath(int page, String mid, int beginDate, int endDate) {
//		return dao.searchFeeLiqBath(page, mid, beginDate, endDate);
		
		LogUtil.printInfoLog("商户结算单查询开始");
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "merFundSettleDataQueryService?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 10);
		map.put("tranCode", "DZ0003");
		map.put("mer_code", mid);
		map.put("start_date", beginDate);
		map.put("end_date", endDate);
//		map.put("mer_batch_no", "");
		map.put("pageNo", page);
		map.put("pageNum", 15);
		
		LogUtil.printInfoLog("商户结算单查询查询参数", map);
		String str = Ryt.requestWithPost(map, url);
		
		CurrentPage<FeeLiqBath> currentPage = null;
		if (null != str && !"".equals(str)) {
			JSONObject jsonObject = JSONObject.fromObject(str);
			String resCode = jsonObject.getString("resCode");
			if ("000".equals(resCode)) {
				int data_count = jsonObject.getInt("data_count");
				String amt_count = jsonObject.getString("amt_count");
				
				String items = jsonObject.getString("items");
				if (null != items && !"".equals(items)) {
					String[] dataList = items.split("\\|");
					FeeLiqBath feeLiqBath = null;
					List<FeeLiqBath> list = new ArrayList<FeeLiqBath>();
					
					for (int i = 0; i < dataList.length; i++) {
						String[] data = dataList[i].split(",");
						feeLiqBath = new FeeLiqBath();
						feeLiqBath.setMid(data[0]);
						feeLiqBath.setBatch(data[2]);
						feeLiqBath.setLiqDate(Integer.valueOf(data[3]));
						feeLiqBath.setTransAmt(Long.valueOf(String.format("%.0f",  Double.valueOf(data[4]) * 100)));
						feeLiqBath.setPurCnt(Integer.valueOf(data[5]));
						feeLiqBath.setRefAmt(Long.valueOf(String.format("%.0f",  Double.valueOf(data[6]) * 100)));
						feeLiqBath.setRefCnt(Integer.valueOf(data[7]));
						feeLiqBath.setManualAdd(Long.valueOf(String.format("%.0f",  Double.valueOf(data[8]) * 100)));
						feeLiqBath.setManualSub(Long.valueOf(String.format("%.0f",  Double.valueOf(data[9]) * 100)));
						feeLiqBath.setFeeAmt(Integer.valueOf(String.format("%.0f",  Double.valueOf(data[10]) * 100)));
						feeLiqBath.setRefFee(Integer.valueOf(String.format("%.0f",  Double.valueOf(data[11]) * 100)));
						feeLiqBath.setLiqAmt(Long.valueOf(String.format("%.0f",  Double.valueOf(data[12]) * 100)));
						list.add(feeLiqBath);
					}
					
					currentPage = new CurrentPage<FeeLiqBath>();
					currentPage.setPageItems(list);
					currentPage.setPageSize(15); // 每页显示数据条数
					currentPage.setPageNumber(page); // 当前页数
					currentPage.setPageTotle(data_count); // 数据总条数
					currentPage.setPagesAvailable(data_count / 15 + 1); // 总页数
					currentPage.setPageAmtSum(Long.valueOf(String.format("%.0f",  Double.valueOf(amt_count) * 100)));
				}
			}
		}
		return currentPage;
		
	}
	// 返回FeeLiqLog对象LIST
	public List<FeeLiqLog> queryLiqFeeLog(String batch) {
		// search_settlement.jsp中调用
		return dao.queryLiqFeeLog(batch);
	}
	// 返回Hlog对象LIST
	public List<Hlog> queryHlog(String batch, String gate) {
		// search_settlement.jsp中调用
		return dao.queryHlog(batch, gate);
	}
	/**
	 * 商户结算单明细查询
	 * 
	 */
	public FileTransfer downloadSettleDetail(Map<String, String> p) throws Exception {
//		String action = p.get("a");
		String mid = p.get("mid");
		String batch = p.get("b");
		//String gate = p.get("g");
		
		String bdate = p.get("bdate");
		String edate = p.get("edate");
		String operId = p.get("operId");
		
//		String name = "明细表";
//		String filename = "";
//		List datelist = null;
//		String[] title = null;
//		String downTime = DateUtil.getNowDateTime();// dateFormat.format(new
//
//		if ("qlog".equals(action)) {
//			datelist = dao.queryLiqFeeLogList(batch);
//			title = new String[] { "商户号", "商户简称", "银行网关", "支付金额", "退款金额", "系统手续费","退回商户手续费" };
//
//			filename = "QLOG";
//		}
//		if ("qhlog".equals(action)) {
//			//datelist = dao.queryHlogList(batch, gate);
//			datelist = dao.queryHlogList(batch);
//			title = new String[] { "商户号", "商户简称",  "订单号", "交易金额", "系统手续费", "交易类型", "系统日期", "交易流水号", "原订单号" };
//
//			filename = "QHLOG";
//		}
		/*if (("list").equals(action)) {
			String kyOrder = p.get("kyOrder");
			String jbOrder = p.get("jbOrder");
			ArrayList<String[]> list = new ArrayList<String[]>();
			title = new String[] { "订单号", "交易金额", "交易日期", "交易时间", "说明" };
			list.add(title);
			if (kyOrder != "") {
				for (int i = 0; i < kyOrder.split(";").length; i++) {
					list.add(new String[] { kyOrder.split(";")[i].split(",")[0], kyOrder.split(";")[i].split(",")[1],
							kyOrder.split(";")[i].split(",")[2], timeConvert(kyOrder.split(";")[i].split(",")[3]),
							"可疑交易" });
				}
			}
			if (jbOrder != "") {
				for (int i = 0; i < jbOrder.split(";").length; i++) {
					list.add(new String[] { jbOrder.split(";")[i].split(",")[0], jbOrder.split(";")[i].split(",")[1],
							jbOrder.split(";")[i].split(",")[2], timeConvert(jbOrder.split(";")[i].split(",")[3]),
							"交易金额不符合" });
				}
			}
			filename = "SETTLEFAIl" + downTime + ".xlsx";
			return new DownloadFile().downloadXLSXFileBase(list, filename, "对账失败订单报表");
		}*/

//		ArrayList<String[]> list = new ArrayList<String[]>();
//		list.add(title);
//
//		Map<Integer, String> gates = RYFMapUtil.getGateMap();
//		RYFMapUtil obj = RYFMapUtil.getInstance();
//		Map<Integer, String> mermap = obj.getMerMap();// (Integer.parseInt(logMid));
//
//		for (int it = 0; it < datelist.size(); it++) {
//			Map m = (Map) datelist.get(it);
//			if ("qlog".equals(action)) {
//				String pa = m.get("pur_amt").toString();
//				String ra = m.get("ref_amt").toString();
//				String fa = m.get("fee_amt").toString();
//				String refFee=m.get("ref_fee").toString();
//				String[] str = { mid, mermap.get(mid), gates.get(m.get("gate")), Ryt.div100(pa),
//						Ryt.div100(ra), Ryt.div100(fa),Ryt.div100(refFee) };
//				list.add(str);
//			}
//			if ("qhlog".equals(action)) {
//				System.out.println(m.get("org_oid"));
//				String amount = m.get("amount").toString();
//				String fee_amt = m.get("fee_amt").toString();
//				Integer type = Integer.parseInt(m.get("type").toString());
//				String sysdate = null;
//				if (type == 4) {
//					sysdate = m.get("mdate").toString();
//				} else {
//					sysdate = m.get("sys_date").toString();
//				}
//				String org_oid=null;
//				if(m.containsKey ("org_oid")){
//					 org_oid= m.get("org_oid").toString();
//				}
//				String[] str = { mid, mermap.get(mid),
//						m.get("oid").toString(), Ryt.div100(amount), Ryt.div100(fee_amt),
//						// AppParam.tlog_type.get(type),
//						RypCommon.getTlogType().get(type), sysdate, m.get("tseq").toString(),
//						org_oid};
//				list.add(str);
//			}
//		}
//		filename += "_" + downTime + ".xlsx";
//		return new DownloadFile().downloadXLSXFileBase(list, filename, name);
		
		LogUtil.printInfoLog("商户结算单明细下载开始");
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "merchantFundSettleDetailDataDownLoadService?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 10);
		map.put("tranCode", "ZH0013");
		map.put("mer_code", mid);
		map.put("mer_batch_no", batch);
		map.put("start_date", bdate);
		map.put("end_date", edate);
		map.put("merPriv", operId + "," + 6 + "," + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		LogUtil.printInfoLog("商户结算单明细下载查询参数", map);
		String str = Ryt.requestWithPost(map, url);
		if (null != str && !"".equals(str)) {
			JSONObject jsonObject = JSONObject.fromObject(str);
			String resCode = jsonObject.getString("resCode");
			LogUtil.printInfoLog("下载请求返回码：" + resCode);
		}
		return null;
	}
	
//	private String timeConvert(String time) {
//		StringBuffer temp = new StringBuffer(time);
//		if (time.indexOf(":") == -1) {
//			temp.insert(2, ":");
//			temp.insert(5, ":");
//		}
//		return temp.toString();
//	}

	/**
	 * 查询商户现有金额（实际余额）
	 * 
	 * @param mid 商户ID
	 * @return 商户余额
	 */
	public String getBalanceById(String mid) {

		return dao.getBalanceById(mid);
	}

	/**
	 * 根据商户名称（商户号）,日期，查询account表中明细
	 * @param mid 商户ID
	 * @param begin_date  查询起始日期
	 * @param end_date 查询结束日期
	 * @param pageIndex 查询页码
	 * @return
	 */
	public CurrentPage<Account> searchAccount(int pageNo, String mid, int begin_date, int end_date) {
		
		return dao.searchAccount(pageNo, AppParam.getPageSize(),mid, begin_date, end_date,null);
	}
	
	/**
	 * 根据商户号，日期，调账类型，调账状态，查询调账表记录
	 * @param mid  商户ID
	 * @param type调账类型
	 * @param state调账状态
	 * @param btdate  查询起始日期
	 * @param etdate查询结束日期
	 * @return
	 */
	public CurrentPage<AdjustAccount> queryAdjust(int pageIndex, String mid, int type, int btdate, int etdate) {
		int pageSize = ParamCache.getIntParamByName("pageSize");
//		return dao.queryAdjustList(pageIndex,pageSize, mid, type, btdate, etdate, state,null);
		
		LogUtil.printInfoLog("手工调账查询开始");
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "reciveManualRecService?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 10);
		map.put("tranCode", "ZH0006");
		map.put("mer_code", mid);
		if (type != 0) {
			map.put("addorsub", type);
		}
		if (btdate != 0) {
			map.put("start_date", btdate);
		}
		if (etdate != 0) {
			map.put("end_date", etdate);
		}
		map.put("pageNo", pageIndex);
		map.put("pageNum", pageSize);
		
		LogUtil.printInfoLog("手工调账查询查询参数", map);
		String str = Ryt.requestWithPost(map, url);
		
		CurrentPage<AdjustAccount> currentPage = null;
		if (null != str && !"".equals(str)) {
			JSONObject jsonObject = JSONObject.fromObject(str);
			String resCode = jsonObject.getString("resCode");
			if ("000".equals(resCode)) {
				int data_count = jsonObject.getInt("data_count");
				
				String items = jsonObject.getString("items");
				if (null != items && !"".equals(items)) {
					String[] dataList = items.split("\\|");
					AdjustAccount adjustAccount = null;
					List<AdjustAccount> list = new ArrayList<AdjustAccount>();
					
					for (int i = 0; i < dataList.length; i++) {
						String[] data = dataList[i].split(",");
						adjustAccount = new AdjustAccount();
						adjustAccount.setMid(data[0]);
						adjustAccount.setType(Integer.valueOf(data[4]));
						adjustAccount.setAccount(Long.valueOf(String.format("%.0f",  Double.valueOf(data[3]) * 100)));
						adjustAccount.setAuditDate(Integer.valueOf(data[6].substring(0, 8)));
						adjustAccount.setAuditTime(Integer.valueOf(data[6].substring(9, 14)));
						adjustAccount.setReason(data[7]);
						list.add(adjustAccount);
					}
					
					currentPage = new CurrentPage<AdjustAccount>();
					currentPage.setPageItems(list);
					currentPage.setPageSize(pageSize); // 每页显示数据条数
					currentPage.setPageNumber(pageIndex); // 当前页数
					currentPage.setPageTotle(data_count); // 数据总条数
					currentPage.setPagesAvailable(data_count / pageSize + 1); // 总页数
				}
			}
		}
		return currentPage;
	}
	
	public List<Hlog> queryHlogs(String batch) {
		// search_settlement.jsp中调用
//		return dao.queryHlog(batch);
		
		LogUtil.printInfoLog("商户结算单查询开始");
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "reciveMerchantFundSettleDetailService?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 10);
		map.put("tranCode", "ZH0007");
		map.put("mer_batch_no", batch);
		map.put("pageNo", 1);
		map.put("pageNum", 15);
		
		LogUtil.printInfoLog("商户结算单明细查询查询参数", map);
		String str = Ryt.requestWithPost(map, url);
		List<Hlog> list = new ArrayList<Hlog>();
		if (null != str && !"".equals(str)) {
			JSONObject jsonObject = JSONObject.fromObject(str);
			String resCode = jsonObject.getString("resCode");
			if ("000".equals(resCode)) {
				String items = jsonObject.getString("items");
				if (null != items && !"".equals(items)) {
					Hlog hlog = null;
					String[] dataList = items.split("\\|");
					for (int i = 0; i < dataList.length; i++) {
						String[] data = dataList[i].split(",");
						hlog = new Hlog();
						hlog.setMid(data[0]);
						hlog.setOid(data[2]);
						hlog.setAmount(Long.valueOf(String.format("%.0f",  Double.valueOf(data[3]) * 100)));
						hlog.setFeeAmt(Integer.valueOf(String.format("%.0f",  Double.valueOf(data[4]) * 100)));
						hlog.setType(Short.valueOf(data[5]));
						hlog.setSysDate(Integer.valueOf(data[6]));
						hlog.setTseq(data[7]);
						list.add(hlog);
					}
				}
			}
		}
		return list;
	}
}
