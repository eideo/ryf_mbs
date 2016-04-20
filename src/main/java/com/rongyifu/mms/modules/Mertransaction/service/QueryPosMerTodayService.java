package com.rongyifu.mms.modules.Mertransaction.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.io.FileTransfer;

import com.ibm.icu.text.SimpleDateFormat;
import com.rongyifu.mms.bean.TradeInfo;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.common.ParamCache;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.LogUtil;

public class QueryPosMerTodayService {
	/**
	 * 接口：查询清结算系统数据 pos当天交易查询<线下当天交易查询>
	 * @param pageNo 页码
	 * @param pageSize 页数
	 * @param mid 商户号
	 * @param deduct_sys_stance 电银流水号
	 * @param innerTermId 电银终端号
	 * @param type 交易类型
	 * @param tstat 交易状态
	 * @param xpe_deduct_refer 银联参考号
	 * @return
	 */
	public CurrentPage<TradeInfo> queryPosMerToday(Integer pageNo,Integer pageSize,String mid,String deduct_sys_stance,String innerTermId,
			Integer  type, String tstat,String xpe_deduct_refer){
		LogUtil.printInfoLog("pos当天交易查询---线下当天交易查询接口");
		if(StringUtils.isBlank(mid)||null == pageNo||null == pageSize||pageNo<=0||pageSize<=0){
			return null;
		}
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage" + "/tradeDataQueryService?"; 
		Map<String, Object> params = new HashMap<String, Object>();
		//消息版本号 
		params.put("version", "10");
		//交易码 
		params.put("tranCode", "DZ0021");
		//平台商户号 
		params.put("mid", mid);
		//电银流水号
		params.put("deduct_sys_stance", deduct_sys_stance);
		//电银终端号 
		params.put("req_mer_term_id", innerTermId);      
		//交易状态 
		params.put("trade_result", tstat);
		//交易类型 
		params.put("trade_type", type);
		//银联参考号
		params.put("deduct_sys_reference", xpe_deduct_refer);
		//页码 
		params.put("pageNo", pageNo);
		//每页显示数据条数：15
		params.put("pageNum", 15);
		LogUtil.printInfoLog("线下当天交易查询参数",params);
		String str = Ryt.requestWithPost(params, url);
		
		if(str !=null && str!=""){
			//解析
			JSONObject jsonObject=JSONObject.fromObject(str);
		    String resCode=jsonObject.getString("resCode");
		    int pageTotle=jsonObject.getInt("data_count");
		    String amtSum=jsonObject.getString("amt_count");
		    String feeSum=jsonObject.getString("fee_count");
		    
		    if(resCode.equals("000")){
		    	CurrentPage<TradeInfo > page = new CurrentPage<TradeInfo>();
				//成功的情况下解析清算系统返回的数据结果
				String items = jsonObject.getString("items");
				if(!items.isEmpty()){
					String[] item = items.split("\\|");
					List<TradeInfo> list = new ArrayList<TradeInfo>();
				for(int i=0; i < item.length ; i++){
					String data =item[i].toString();
					TradeInfo	trade = new TradeInfo();
					String[] tradeInfo =data.split(",");
					trade.setXpepMercode(tradeInfo[0]); 	//商户号
					trade.setTseq(tradeInfo[1]);   //电银交易编号
					trade.setInnerTermId(tradeInfo[5]);   //电银终端号
					trade.setXpeDeductTrace(tradeInfo[2]);  //电银流水号
					trade.setAmount(Long.parseLong(Ryt.mul100(tradeInfo[3])));  //交易金额(元)
					trade.setMerFeeModel(tradeInfo[4]);  //系统手续费(元)
					trade.setTstat(tradeInfo[11]); //交易状态(取值银行返回码 )
					trade.setSysDate(Long.parseLong(tradeInfo[7])); //系统日期
					trade.setXpeDeductRefer(tradeInfo[9]);   //银联参考号
					trade.setType(Short.parseShort(tradeInfo[8]));  //交易类型
					trade.setBk_seq1(tradeInfo[10]);   //交易类型
					trade.setXpeDeductResp(tradeInfo[11]);  //银行返回码 
					list.add(trade);
				}
				 page.setPageItems(list);
				 page.setPageTotle(pageTotle);
		         page.setPageNumber(pageNo);
		         page.setPageSize(AppParam.getPageSize());
		         if (pageTotle % 15 == 0) {
		        	 page.setPagesAvailable(pageTotle / 15);
		         }else{
		        	 page.setPagesAvailable(pageTotle / 15 + 1 );
		         }
		         Map<String,String> maps = new HashMap<String,String>();
		         maps.put(AppParam.AMT_SUM, Ryt.mul100(amtSum));
		         maps.put(AppParam.SYS_AMT_FEE_SUM,Ryt.mul100(feeSum));
		         if(maps != null){
		        	 for (String key : maps.keySet()){
		        		 page.setSumResult(key, maps.get(key));
		        	 }
		         }
				return page;
			}else{
				return null;
			}
		}
	}
		return null;
	}
	
	/**
	 * pos当天交易查询 --下载
	 * @param operId 操作员号
	 * @param mid 平台商户号 
	 * @param deduct_sys_stance  电银流水号
	 * @param innerTermId 电银终端号 
	 * @param type 交易类型 
	 * @param tstat 交易状态 
	 * @param xpe_deduct_refer 银联参考号
	 * @return
	 * @throws Exception
	 */
	public FileTransfer downloadToday(Integer operId,String mid,String deduct_sys_stance,String innerTermId,Integer  type, String tstat,String xpe_deduct_refer) throws Exception {
		 LogUtil.printInfoLog("pos当天交易查询--下载");
		 String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "tradeDataDownLoadService?";
		 Map<String,Object> maps = new HashMap<String,Object>();
		   //消息版本号 
			maps.put("version", "10");
			//交易码 
			maps.put("tranCode", "DZ0023");
			//平台商户号 
			maps.put("mid", mid);
			//商户订单号 
			maps.put("deduct_sys_stance", deduct_sys_stance);
			//电银终端号 
			maps.put("req_mer_term_id", innerTermId);      
			//交易状态 
			maps.put("trade_result", tstat);
			//交易类型 
			maps.put("trade_type", type);
			//银联参考号
			maps.put("deduct_sys_reference", xpe_deduct_refer);
		   //私有域:operId + ftp数据源 +时间
		    maps.put("merPriv", operId + "," +3 +"," + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		LogUtil.printInfoLog("pos当天交易查询--下载参数", maps);
		String str = Ryt.requestWithPost(maps, url);
		if (str != null && !"".equals(str)) {
			JSONObject object = JSONObject.fromObject(str);
			String resCode = object.getString("resCode");
			LogUtil.printInfoLog("下载请求返回码：" + resCode);
		}
		return null;
	}
	/**
	 *接口：查询清结算系统数据 pos历史交易查询<线下历史交易查询>
	 * @param pageNo 页码
	 * @param pageSize 页数
	 * @param mid 商户号
	 * @param deduct_sys_stance 电银流水号
	 * @param innerTermId 电银终端号
	 * @param type 交易类型
	 * @param tstat 交易状态
	 * @param xpe_deduct_refer 银联参考号
	 * @return
	 */
	public CurrentPage<TradeInfo> queryMerHlogs(Integer pageNo,Integer pageSize,String mid,String deduct_sys_stance,
			String innerTermId,Integer  type, String tstat,Integer beginDate,Integer endDate,String xpe_deduct_refer){
		LogUtil.printErrorLog("pos历史交易查询<线下历史交易查询接口>");
		if(StringUtils.isBlank(mid)||null == pageNo||null == pageSize||pageNo<=0||pageSize<=0){
			return null;
		}
		String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage" + "/tradeDataQueryService?"; 
		Map<String,Object> maps = new HashMap<String, Object>();
		//消息版本号 
		maps.put("version", "10");
		//交易码 
		maps.put("tranCode", "DZ0022");
		//平台商户号 
		maps.put("mid", mid);
		//商户订单号 
		maps.put("deduct_sys_stance", deduct_sys_stance);
		//电银终端号 
		maps.put("req_mer_term_id", innerTermId);      
		//交易状态 
		maps.put("trade_result", tstat);
		//交易类型 
		maps.put("trade_type", type);
		//start_date 	查询起始交易日期 YYYYMMDD
		maps.put("start_date", beginDate);
		//end_date	  查询结束交易日期
		maps.put("end_date", endDate);
		//银联参考号
		maps.put("deduct_sys_reference", xpe_deduct_refer);
		//页码 
		maps.put("pageNo", pageNo);
		//每页显示数据条数：15
		maps.put("pageNum", 15);
		LogUtil.printInfoLog("线下历史交易查询参数",maps);
		String str = Ryt.requestWithPost(maps, url);
		
		if(str != null && str !=""){
			JSONObject  json = JSONObject.fromObject(str);
			String resCode = json.getString("resCode");
			int pageTotle = json.getInt("data_count");
			String amtSum = json.getString("amt_count");
			String feeSum = json.getString("fee_count");
					
	    if(resCode.equals("000")){
	    	CurrentPage<TradeInfo> page = new CurrentPage<TradeInfo>();
	    	//成功的情况下，解析清算系统返回的数据值
	    	String items = json.getString("items");
	    	if(!items.isEmpty()){
	    		String[] item = items.split("\\|");
	    		List<TradeInfo> list = new ArrayList<TradeInfo>();
	    		for(int i=0;i<item.length;i++){
	    			String data = item[i].toString();
	    			TradeInfo trade = new TradeInfo();
	    			String[]  tradeInfo = data.split(",");
	    			trade.setXpepMercode(tradeInfo[0]); 	//商户号
					trade.setTseq(tradeInfo[1]);   //电银交易编号
					trade.setInnerTermId(tradeInfo[5]);   //电银终端号
					trade.setXpeDeductTrace(tradeInfo[2]);  //电银流水号
					trade.setAmount(Long.parseLong(Ryt.mul100(tradeInfo[3])));  //交易金额(元)
					trade.setMerFeeModel(tradeInfo[4]);  //系统手续费(元)
					trade.setTstat(tradeInfo[11]); //交易状态(取值银行返回码 )
					trade.setSysDate(Long.parseLong(tradeInfo[7])); //系统日期
					trade.setXpeDeductRefer(tradeInfo[9]);   //银联参考号
					trade.setType(Short.parseShort(tradeInfo[8]));  //交易类型
					trade.setBk_seq1(tradeInfo[10]);   //银联参考号
					trade.setXpeDeductResp(tradeInfo[11]);  //银行返回码 
					list.add(trade);
	    		}
	    		page.setPageItems(list);
	    		page.setPageTotle(pageTotle);
		    	page.setPageNumber(pageNo);
		    	page.setPageSize(AppParam.getPageSize());
		    	 if (pageTotle % 15 == 0) {
		        	 page.setPagesAvailable(pageTotle / 15);
		         }else{
		        	 page.setPagesAvailable(pageTotle / 15 + 1 );
		         }
		    	Map<String,Object> map = new HashMap<String,Object>();
		    	map.put(AppParam.AMT_SUM, Ryt.mul100(amtSum));
		    	map.put(AppParam.SYS_AMT_FEE_SUM, Ryt.mul100(feeSum));
		    	
		    	if(map != null){
		    		for(String key :map.keySet()){
		    			page.setSumResult(key, map.get(key));
		    		}
		    	}
	    		return page;
	    	}else{
	    		return null;
	    	}
	    }
	}
		return null;
	}
	
	/**
	 * pos历史交易查询下载
	 * @param operId 操作员号
	 * @param mid 平台商户号 
	 * @param deduct_sys_stance 电银流水号
	 * @param innerTermId 电银终端号 
	 * @param type 交易类型 
	 * @param beginDate 查询起始交易日期 YYYYMMDD
	 * @param endDate 查询结束交易日期
	 * @param xpe_deduct_refer 银联参考号
	 * @return
	 * @throws Exception
	 */
	public FileTransfer downloadHlog(Integer operId,String mid,String deduct_sys_stance,String innerTermId,Integer  type, 
			String tstat,Integer beginDate,Integer endDate,String xpe_deduct_refer) throws Exception {
		 LogUtil.printInfoLog("pos历史交易查询下载");
		 String url = ParamCache.getStrParamByName("QSXT_URL") + "backstagemamage/" + "tradeDataDownLoadService?";
		 Map<String,Object> maps = new HashMap<String,Object>();
		   //消息版本号 
			maps.put("version", "10");
			//交易码 
			maps.put("tranCode", "DZ0024");
			//平台商户号 
			maps.put("mid", mid);
			//商户订单号 
			maps.put("deduct_sys_stance", deduct_sys_stance);
			//电银终端号 
			maps.put("req_mer_term_id", innerTermId);      
			//交易状态 
			maps.put("trade_result", tstat);
			//交易类型 
			maps.put("trade_type", type);
			//start_date 	查询起始交易日期 YYYYMMDD
			maps.put("start_date", beginDate);
			//end_date	  查询结束交易日期
			maps.put("end_date", endDate);
			//银联参考号
			maps.put("deduct_sys_reference", xpe_deduct_refer);
		   //私有域:operId + ftp数据源 +时间
		    maps.put("merPriv", operId + "," +4 +"," + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		LogUtil.printInfoLog("pos历史交易查询下载参数", maps);
		String str = Ryt.requestWithPost(maps, url);
		if (str != null && !"".equals(str)) {
			JSONObject object = JSONObject.fromObject(str);
			String resCode = object.getString("resCode");
			LogUtil.printInfoLog("下载请求返回码：" + resCode);
		}
		return null;
	}
}
