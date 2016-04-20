package com.rongyifu.mms.refund;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.dao.SystemDao;
import com.rongyifu.mms.utils.LogUtil;

public class RefundUtil {
	
	/**
	 * 支持联机退款的银行网关
	 */
	public static final String gateList = "";
	
	// 退款申请被受理
	public static final String ORDER_STATUS_ACCEPT  = "5";
	// 失败状态
	public static final String ORDER_STATUS_FAILURE = "3";
	// 处理中
	public static final String ORDER_STATUS_PROCESSED = "1";
	// 请求银行失败
	public static final String QUERT_BANK_FAILURE = "4";
	//退款成功
	public static final String ORDER_STATUS_SUCCESS = "2";
	
	private static Map<String, String> reFundGateList = null;
	
	public static Map<String, String> getRefundGateList(){
		return reFundGateList;
	}
	static{
		reFundGateList = new HashMap<String, String>();
//		reFundGateList.put("56000", "com.rongyifu.mms.refund.bank.AlipayHHRefund");
//		reFundGateList.put("56001", "com.rongyifu.mms.refund.bank.AlipaySHRefund");
//		reFundGateList.put("56002", "com.rongyifu.mms.refund.bank.AlipayXPRefund");
//		reFundGateList.put("90009", "com.rongyifu.mms.refund.bank.AlipayHHRefund");
//		reFundGateList.put("90010", "com.rongyifu.mms.refund.bank.AlipaySHRefund");
//		reFundGateList.put("90011", "com.rongyifu.mms.refund.bank.AlipayXPRefund");
//		//银联网关对应的退款类
//		reFundGateList.put("55000", "com.rongyifu.mms.refund.bank.UnionPayRefund");
//		reFundGateList.put("55001", "com.rongyifu.mms.refund.bank.UPMPRefund");
//		reFundGateList.put("90000", "com.rongyifu.mms.refund.bank.CMBRefund");
//		reFundGateList.put("4", "com.rongyifu.mms.refund.bank.KqRefund");
//		reFundGateList.put("90016", "com.rongyifu.mms.refund.bank.UnionPayRefund2");
//		reFundGateList.put("10", "com.rongyifu.mms.refund.bank.KqRefund");
//		reFundGateList.put("11", "com.rongyifu.mms.refund.bank.KqRefund");
//		reFundGateList.put("56113", "com.rongyifu.mms.refund.bank.EposRefund");
		
	}
	//md5加密
	public static String md5Encrypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}
	//请求银行
	@SuppressWarnings("rawtypes")
	public static String requestByPostwithURL(
			Map<String, String> requestParaMap, String url) throws IOException {
		LogUtil.printInfoLog(requestParaMap.toString());
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		String sResponseBody = "";
		NameValuePair[] nameValuePairs = null;
		NameValuePair nameValuePair = null;
		try {
			if (requestParaMap != null && requestParaMap.size() > 0) {
				nameValuePairs = new NameValuePair[requestParaMap.size()];
				int i = 0;
				Iterator it = requestParaMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry element = (Map.Entry) it.next();
					nameValuePair = new NameValuePair();
					nameValuePair.setName(String.valueOf(element.getKey()));
					nameValuePair.setValue(String.valueOf(element.getValue()));
					nameValuePairs[i++] = nameValuePair;
				}
				method.setRequestBody(nameValuePairs);
				httpClient.executeMethod(method);
				int resCode = method.getStatusCode();
				if (resCode == HttpStatus.SC_OK) {
//					sResponseBody = method.getResponseBodyAsString();
					InputStream input = method.getResponseBodyAsStream();
					sResponseBody = Ryt.readStream(input);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return sResponseBody;
	}
	
	/*如下是银联退款请求处理代码，
	 * 和requestByPostwithURL代码一样,
	 * 建议进行优化
	   请求银行*/
		@SuppressWarnings("rawtypes")
		public static String requestByPostwithURL_unionpay(
				Map<String, String> requestParaMap, String url) throws IOException {
						
			LogUtil.printInfoLog("unionpay.refund", requestParaMap);
			HttpClient httpClient = new HttpClient();
			PostMethod method = new PostMethod(url);
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			String sResponseBody = "";
			NameValuePair[] nameValuePairs = null;
			NameValuePair nameValuePair = null;
			try {
				if (requestParaMap != null && requestParaMap.size() > 0) {
					nameValuePairs = new NameValuePair[requestParaMap.size()];
					int i = 0;
					Iterator it = requestParaMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry element = (Map.Entry) it.next();
						nameValuePair = new NameValuePair();
						nameValuePair.setName(String.valueOf(element.getKey()));
						nameValuePair.setValue(String.valueOf(element.getValue()));
						nameValuePairs[i++] = nameValuePair;
					}
					method.setRequestBody(nameValuePairs);
					httpClient.executeMethod(method);
					int resCode = method.getStatusCode();
					if (resCode == HttpStatus.SC_OK) {
//						sResponseBody = method.getResponseBodyAsString();
						InputStream input = method.getResponseBodyAsStream();
						sResponseBody = Ryt.readStream(input);
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			} finally {
				method.releaseConnection();
			}
			return sResponseBody;
		}
		
	/**
	 * 查询ewp地址
	 */
	public static String querywep() {
		SystemDao dao = new SystemDao();
		return dao.queryewp();
	}
}
