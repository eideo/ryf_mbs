package com.rongyifu.mms.modules.Mertransaction.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.directwebremoting.io.FileTransfer;

import com.ibm.icu.text.SimpleDateFormat;
import com.rongyifu.mms.bean.Hlog;
import com.rongyifu.mms.bean.OrderInfo;
import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.modules.Mertransaction.dao.QueryMerMerTodayDao;
import com.rongyifu.mms.transaction.ManualRequest;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.LogUtil;

public class QueryMerMerHlogDetailService {
//	private MerQueryHlogDetailDao merQueryHlogDetailDao=new MerQueryHlogDetailDao();
	
	/**
	 * 根据条件查询交易明细
	 * @return CurrentPage
	 * @throws ParseException 
	 */
	public CurrentPage<OrderInfo> queryHlogDetail(Integer pageNo, String mid, String tesq, Integer tstat, Integer type,
					String oid, Integer gid, String date, Integer bdate, Integer edate,Integer bkCheck){
		LogUtil.printInfoLog("历史收款明细查询开始");
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "tradeDataQueryService?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", 10);
		map.put("tranCode", "DZ0002");
		map.put("mid", mid);
		map.put("start_date", bdate);
		map.put("end_date", edate);
		if (tstat != null) {
			map.put("trade_result", tstat);
		}
		if (type != null) {
			map.put("trade_type", type);
		}
		map.put("oid", oid);
		map.put("trade_id", tesq);
		map.put("pageNo", pageNo);
		map.put("pageNum", 15);
		
		LogUtil.printInfoLog("历史收款明细查询查询参数", map);
		String str = Ryt.requestWithPost(map, url);
		
		CurrentPage<OrderInfo> currentPage = new CurrentPage<OrderInfo>();
		if (null != str && !"".equals(str)) {
			JSONObject jsonObject = JSONObject.fromObject(str);
			String resCode = jsonObject.getString("resCode");
			if ("000".equals(resCode)) {
				int data_count = jsonObject.getInt("data_count");
				String amt_count = jsonObject.getString("amt_count");
				String fee_count = jsonObject.getString("fee_count");
				
				String items = jsonObject.getString("items");
				if (null != items && !"".equals(items)) {
					String[] dataList = items.split("\\|");
					OrderInfo orderInfo = null;
					List<OrderInfo> list = new ArrayList<OrderInfo>();
					
					for (int i = 0; i < dataList.length; i++) {
						String[] data = dataList[i].split(",");
						orderInfo = new OrderInfo();
						orderInfo.setTseq(data[4]);
						orderInfo.setMid(data[0]);
						orderInfo.setOid(data[1]);
						orderInfo.setMdate(Integer.valueOf(data[2]));
						orderInfo.setAmount(Long.valueOf(String.format("%.0f",  Double.valueOf(data[5]) * 100)));
						orderInfo.setTstat(Short.valueOf(data[8]));
						orderInfo.setType(Short.valueOf(data[7]));
						orderInfo.setAmount(Long.valueOf(String.format("%.0f",  Double.valueOf(data[5]) * 100)));
						orderInfo.setFeeAmt(Integer.valueOf(String.format("%.0f",  Double.valueOf(data[6]) * 100)));
						orderInfo.setSysDate11(Long.parseLong(data[9]));
						list.add(orderInfo);
					}
					
					currentPage.setPageItems(list);
					currentPage.setPageSize(15); // 每页显示数据条数
					currentPage.setPageNumber(pageNo); // 当前页数
					currentPage.setPageTotle(data_count); // 数据总条数
					if (data_count % 15 == 0) {
						currentPage.setPagesAvailable(data_count / 15); // 总页数
					} else {
						currentPage.setPagesAvailable(data_count / 15 + 1); // 总页数
					}
					currentPage.setSumResult("amtSum", Long.valueOf(String.format("%.0f",  Double.valueOf(amt_count) * 100)));
					currentPage.setSumResult("sysAmtFeeSum", Long.valueOf(String.format("%.0f",  Double.valueOf(fee_count) * 100)));
				}
			}
		}
		return currentPage;
	}
	
	/**
	 * 商户的交易明细下载
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public FileTransfer downloadDetail_MER(Integer operId, String mid, Integer tesq, Integer tstat, Integer type,
			String oid, Integer gateRouteId, String date, Integer bdate, Integer edate) throws Exception {
//		  CurrentPage<OrderInfo> hlogListPage=merQueryHlogDetailDao.queryHlogDetail(1,-1, mid, tesq, tstat, type, oid, gateRouteId, date, bdate, edate,null,null,null,
//				  begintrantAmt, endtrantAmt);
//		  
//		List<OrderInfo> hlogList=hlogListPage.getPageItems();
//		ArrayList<String[]> list = new ArrayList<String[]>();
//		Map<Integer, String> gates = RYFMapUtil.getGateMap();
//		long totleAmount = 0;
//		long totleFeeAmt = 0;
//		list.add("序号,电银流水号,商户号,商户订单号,商户日期,交易金额(元),交易状态,交易类型,交易银行,系统手续费(元),系统日期".split(","));
//		int i = 0;
//
//		for (OrderInfo h : hlogList) {
//			String[] str = { (i + 1) + "", h.getTseq() + "", h.getMid() + "", h.getOid(), h.getMdate() + "",
//					Ryt.div100(h.getAmount()), AppParam.tlog_tstat.get(Integer.parseInt(h.getTstat() + "")),
//					AppParam.tlog_type.get(Integer.parseInt(h.getType() + "")), gates.get(h.getGate()),
//					Ryt.div100(h.getFeeAmt()), h.getSysDate() + "" };
//			totleAmount += h.getAmount();
//			totleFeeAmt += h.getFeeAmt();
//			i += 1;
//			list.add(str);
//		}
//		String[] str = { "总计:" + i + "条记录", "", "", "", "", Ryt.div100(totleAmount) + "", "", "", "",
//				Ryt.div100(totleFeeAmt) + "", "" };
//		list.add(str);
//		String filename = "MERHLOG_" + DateUtil.today() + ".xlsx";
//		String name = "交易明细表";
//		return new DownloadFile().downloadXLSXFileBase(list, filename, name);
		  
		  LogUtil.printInfoLog("历史收款明细查询下载开始");
		  String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "tradeDataDownLoadService?";
		  Map<String, Object> map = new HashMap<String, Object>();
		  map.put("version", 10);
		  map.put("tranCode", "DZ0009");
		  map.put("mid", mid);
		  map.put("start_date", bdate);
		  map.put("end_date", edate);
		  if (tstat != null) {
			  map.put("trade_result", tstat);
		  }
		  if (type != null) {
			  map.put("trade_type", type);
		  }
		  map.put("oid", oid);
		  map.put("trade_id", tesq);
		  map.put("merPriv", operId + "," + 2 + "," + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		  
		  LogUtil.printInfoLog("历史收款明细查询查询参数", map);
		  String str = Ryt.requestWithPost(map, url);
		  if (null != str && !"".equals(str)) {
			  JSONObject jsonObject = JSONObject.fromObject(str);
			  String resCode = jsonObject.getString("resCode");
			  LogUtil.printInfoLog("下载请求返回码：" + resCode);
		  }
		  return null;

	}
	
	/**
	 * @return批量通知
	 */
	public String batchNotifyMerBkUrl(List<String> tseqList,String table){
		StringBuilder msg = new StringBuilder();
		try {
			for (String tseq : tseqList) {
				String rslt = notifyMerBkUrl(tseq, table);
				msg.append(tseq).append(":").append(rslt).append(",");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "请求失败!"+e.getMessage();
		}
		return msg.toString();
	}
	
	/**
	 * 获取明细查询和当天交易查询中手工发送请求的地址 通知商户后台
	 */
	public String notifyMerBkUrl(String tseq, String table) {
		QueryMerMerTodayDao dao = new QueryMerMerTodayDao();
		Hlog hlog = dao.queryHlogById(tseq, table);
		if (hlog.getBkUrl() == null || hlog.getBkUrl().trim().equals("")) return "请求失败!";
		
		 String msg = new ManualRequest().requestBkUrl(hlog);
		 if("请求成功!".equals(msg)){
			 dao.updateNotifyStatus("(" + tseq + ")",table,1);//1 已通知
		 }
		 return msg;
	}
}
