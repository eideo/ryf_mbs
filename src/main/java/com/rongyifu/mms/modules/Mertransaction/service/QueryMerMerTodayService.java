package com.rongyifu.mms.modules.Mertransaction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.directwebremoting.annotations.RemoteMethod;

import com.rongyifu.mms.bean.Hlog;
import com.rongyifu.mms.bean.OrderInfo;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.modules.Mertransaction.dao.QueryMerMerTodayDao;
import com.rongyifu.mms.transaction.ManualRequest;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.DateUtil;
import com.rongyifu.mms.utils.LogUtil;

public class QueryMerMerTodayService {
	private QueryMerMerTodayDao querymermerdao=new QueryMerMerTodayDao();
	
	/**
	 * 根据条件查询当天交易
	 * @return CurrentPage
	 */
	@RemoteMethod
	public CurrentPage<OrderInfo> queryMerToday(Integer pageNo, String mid, Integer gate, Integer tstat, Integer type,
					String tseq, String oid, Integer gid, String bkseq) {
		//return querymermerdao.queryMerToday(pageNo, AppParam.getPageSize(),mid,gate,tstat,type, tseq,oid,gid,bkseq,null,begintrantAmt,endtrantAmt);
		LogUtil.printInfoLog("当天交易查询开始");
		String url = ParamCache.getStrParamByName("QSXT_URL")+"backstagemamage/"+"tradeDataQueryService";
		Map<String, Object> reqPramas = new HashMap<String, Object>();
		reqPramas.put("tranCode", "DZ0001");
		reqPramas.put("version", "10");
		reqPramas.put("mid", mid);
		reqPramas.put("trade_id", tseq);
		reqPramas.put("oid", oid);
		reqPramas.put("trade_result", tstat);
		reqPramas.put("trade_type", type);
		reqPramas.put("pageNo", pageNo);
		reqPramas.put("pageNum", AppParam.getPageSize());
		LogUtil.printInfoLog("当天交易查询参数", reqPramas);
		String resStr=Ryt.requestWithPost(reqPramas,url);
		
		if(resStr !=null && resStr!=""){
			//解析
			JSONObject json=JSONObject.fromObject(resStr);
		    String resCode=json.getString("resCode");
		    String pageTotle=json.getString("data_count");
		    String amtSum=json.getString("amt_count");
		    String feeSum=json.getString("fee_count");
		  
		     if(resCode.equals("000")){
		    	 CurrentPage<OrderInfo> page =new CurrentPage<OrderInfo>();
		    	 page.setPageTotle(Integer.parseInt(pageTotle));
		    	 page.setPageNumber(pageNo);
		    	 page.setPageSize(AppParam.getPageSize());
		    	 Map<String, String> sumSQLMap=new HashMap<String,String>();
		 		sumSQLMap.put(AppParam.AMT_SUM, Ryt.mul100(amtSum));
		 		sumSQLMap.put(AppParam.SYS_AMT_FEE_SUM, Ryt.mul100(feeSum));
		 		if (sumSQLMap != null) {
					for (String key : sumSQLMap.keySet()) {
						page.setSumResult(key, sumSQLMap.get(key));
					}
				}
				//成功
		    	 String items = json.getString("items");
		    	 if(!items.isEmpty()){
		    		 String[] itemArr = items.split("\\|");
			    	 List<OrderInfo> orderlist = new ArrayList<OrderInfo>();
			    	
			    	 for (int i = 0; i < itemArr.length; i++) {
			    		 String item =itemArr[i].toString();
			    		 OrderInfo order=new OrderInfo();
			    		 String[] order1= item.split(",");
			    		 order.setMid(order1[0]);
			    		 order.setOid(order1[1]);
			    		 order.setMdate(Integer.parseInt(order1[2]));
			    		 order.setSysTime(Integer.parseInt(order1[3]));
			    		 order.setTseq(order1[4]);
			    		 order.setAmount(Long.parseLong(Ryt.mul100(order1[5])));
			    		 order.setFeeAmt(Integer.parseInt(Ryt.mul100(order1[6])));
			    		 order.setType(Short.parseShort(order1[7]));
			    		 order.setTstat(Short.parseShort(order1[8]));
			    		 order.setSysDate11(Long.parseLong(order1[9]));
			    		 order.setSysTime(Integer.parseInt(order1[10]));
			    		 order.setBk_seq1((order1[13]));
			    		 order.setBk_seq2(order1[14]);
			    		 //order.setBkResp(order1[15]);
			    		 orderlist.add(order);
			    	 }
			    	 page.setPageItems(orderlist);
			    	 return  page;
		    	 }
		    	 else{
		    		 return null;
		    	 }
		    	 
			}
		    
		}
		return null;
	}
	
	/**
	 * @param tseqList
	 * @param table
	 * @return
	 * 批量通知商户
	 */
	public String batchNotifyMerBkUrl(List<String> tseqList,String table){
		StringBuilder msg = new StringBuilder();
		List<String> successTesqs = new ArrayList<String>();
		try {
			for (String tseq : tseqList) {
				String rslt = notifyMerBkUrl(tseq, table);
				msg.append(rslt).append(",");
				if("请求成功!".equals(rslt)){
					successTesqs.add(tseq);
				 }
			}
			int size = successTesqs.size();
			if(size>0){
				StringBuilder inStr = new StringBuilder("(");
				for (int i=0;i<size;i++) {
					String tseq = successTesqs.get(i);
					inStr.append(Ryt.sql(tseq)).append(i==size-1?")":",");
				}
				querymermerdao.updateNotifyStatus(inStr.toString(),table,1);//已通知
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "请求失败";
		}
		return msg.toString();
	}
	/**
	 * 获取明细查询和当天交易查询中手工发送请求的地址 通知商户后台
	 */
	public String notifyMerBkUrl(String tseq, String table) {
		String msg="";
		Hlog hlog= null;
		try {
			hlog = querymermerdao.queryHlogById(tseq, table);
			if (hlog.getBkUrl() == null || hlog.getBkUrl().trim().equals("")) return hlog.getOid()+" 请求失败!";
			
			msg= hlog.getOid()+" "+new ManualRequest().requestBkUrl(hlog);
		} catch (Exception e) {
			LogUtil.printErrorLog("tseq=" + tseq, e);
			msg= hlog.getOid()+ " 请求失败";
		}
		return msg;
	}
	
	/**
	 * 商户当天交易下载
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public String  downloadToday_MER(String mid, Integer operId, Integer tstat, Integer type,
			String tseq, String oid, Integer gateRouteId, String bkseq) throws Exception {

		 	LogUtil.printInfoLog("当天交易下载通知开始");
		 	long sysDate = DateUtil.getIntDateTime();
			String url = ParamCache.getStrParamByName("QSXT_URL")+"backstagemamage/"+"tradeDataDownLoadService";
			String merPriv=operId +","+"1"+","+sysDate;
			Map<String, Object> reqPramas = new HashMap<String, Object>();
			reqPramas.put("tranCode", "DZ0008");
			reqPramas.put("version", "10");
			reqPramas.put("mid", mid);
			//reqPramas.put("operId", operId);
			//reqPramas.put("ftpSource", 1);
			//reqPramas.put("tseq", tseq);
			reqPramas.put("oid", oid);
			//reqPramas.put("tstat", tstat);
			reqPramas.put("trade_type", type);
			reqPramas.put("merPriv", merPriv);
			//reqPramas.put("sysDate", sysDate);
			LogUtil.printInfoLog("当天交易查询参数", reqPramas);
			String resStr=Ryt.requestWithPost(reqPramas,url);
			LogUtil.printInfoLog("请求返回:"+resStr);
		 return "请稍后去下载页面下载";

	}

}
