package com.rongyifu.mms.modules.Merrefundmanage.dao;

import java.util.HashMap;
import java.util.Map;

import com.rongyifu.mms.bean.RefundLog;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.db.PubDao;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.LogUtil;

@SuppressWarnings("rawtypes")
public class QueryMerRefundLogsDao extends PubDao {

	/**
	 * 退款查询
	 * 
	 * @return CurrentPage<RefundLog>
	 */
	@SuppressWarnings("unchecked")
	public CurrentPage<RefundLog> queryRefundLogs(Integer pageNo,Integer pageSize, String mid, String stat, String tseq,
			Integer dateState, Integer bdate, Integer edate, Integer gate,String orgid, Integer vstate, Integer gid, Integer mstate,
			Integer refundType, String begintrantAmt, String endtrantAmt) {
		StringBuffer sqlBuff = this.getRefundLogsSql(mid, stat, tseq,dateState, bdate, edate, gate, orgid, vstate, gid, mstate,
				refundType, begintrantAmt, endtrantAmt);
		String sqlFetchRows = sqlBuff.toString();
		String sqlCountRows = sqlFetchRows.replace("r.*", "count(*)");
		String sqlSumAmt = sqlFetchRows.replace("r.*","COALESCE(sum(r.ref_amt),0)");
//		String sqlMerRefFeeSum = sqlFetchRows.replace("r.*","COALESCE(sum(r.mer_fee),0)");
		Map<String, String> sumMap = new HashMap<String, String>();
		sumMap.put(AppParam.REF_FEE_SUM, sqlSumAmt);
//		sumMap.put(AppParam.MER_REF_FEE_SUM, sqlMerRefFeeSum);
		return queryForPage(sqlCountRows, sqlFetchRows, pageNo, pageSize,RefundLog.class, sumMap);
	}

	// 退款查询的sql语句
	private StringBuffer getRefundLogsSql(String mid, String stat, String tseq,Integer dateState, Integer bdate, Integer edate, Integer gate,
			String orgid, Integer vstate, Integer gid, Integer mstate,Integer refundType, String begintrantAmt, String endtrantAmt) {
		StringBuffer condition = new StringBuffer("select r.* from refund_log as r where 1=1");
		if (!Ryt.empty(mid))
			condition.append(" AND r.mid = ").append(Ryt.addQuotes(mid));
		if (!Ryt.empty(stat))
			condition.append(" AND (r.stat = ").append(Ryt.sql(stat)).append(")");
		if (!Ryt.empty(tseq))
			condition.append(" AND r.tseq = ").append(Ryt.addQuotes(tseq));
		if (refundType != null)
			condition.append(" AND r.refund_type=").append(refundType);
		if (dateState == 1) {// 1为申请日期2为确认日期3为经办日期4为审核日期
			if (bdate != null)
				condition.append(" AND r.mdate >= ").append(bdate);
			if (edate != null)
				condition.append(" AND r.mdate <= ").append(edate);
		} else if (dateState == 2) {
			if (bdate != null)
				condition.append(" AND r.req_date >= ").append(bdate);
			if (edate != null)
				condition.append(" AND r.req_date <= ").append(edate);
		} else if (dateState == 3) {
			if (bdate != null)
				condition.append(" AND r.pro_date >= ").append(bdate);
			if (edate != null)
				condition.append(" AND r.pro_date <= ").append(edate);
		} else if (dateState == 4) {
			if (bdate != null)
				condition.append(" AND r.ref_date >= ").append(bdate);
			if (edate != null)
				condition.append(" AND r.ref_date <= ").append(edate);
		}
		if (gate != null)
			condition.append(" AND r.gate = ").append(gate);
		if (!Ryt.empty(orgid))
			condition.append(" AND r.org_oid = ").append(Ryt.addQuotes(orgid));
		if (vstate != null)
			condition.append(" AND r.vstate = ").append(vstate);
		if (gid != null)
			condition.append(" AND r.gid = ").append(gid);
		if (!Ryt.empty(begintrantAmt))
			condition.append(" AND r.ref_amt >= ").append(Ryt.mul100toInt(begintrantAmt));
		if (!Ryt.empty(endtrantAmt))
			condition.append(" AND r.ref_amt <= ").append(Ryt.mul100toInt(endtrantAmt));
		return condition;
	}

	/**
	 * 根据Id查询RefundLog
	 * 
	 * @param id
	 * @return RefundLog
	 */
	public RefundLog queryRefundLogById(Integer id) {
		RefundLog refundLog = null;
		try {
			refundLog = queryForObject("select r.* from refund_log r where r.id = ?",
					new Object[] { id }, RefundLog.class);
		} catch (Exception e) {
			LogUtil.printErrorLog("id=" + id, e);
		}
		return refundLog;
	}

}
