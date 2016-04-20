package com.rongyifu.mms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.directwebremoting.io.FileTransfer;

import com.rongyifu.mms.bean.LoginUser;
import com.rongyifu.mms.bean.MMSNotice;
import com.rongyifu.mms.bean.Minfo;
import com.rongyifu.mms.bean.MinfoH;
import com.rongyifu.mms.bean.OperInfo;
import com.rongyifu.mms.bean.OperLog;
import com.rongyifu.mms.common.AppParam;
import com.rongyifu.mms.common.Constant;
import com.rongyifu.mms.common.Ryt;
import com.rongyifu.mms.dao.MerInfoDao;
import com.rongyifu.mms.dao.MerOperDao;
import com.rongyifu.mms.dao.OperAuthDao;
import com.rongyifu.mms.ewp.EWPService;
import com.rongyifu.mms.merchant.MenuBean;
import com.rongyifu.mms.merchant.MenuService;
import com.rongyifu.mms.rmi.client.RypayRmiClient;
import com.rongyifu.mms.utils.CurrentPage;
import com.rongyifu.mms.utils.DateUtil;
import com.rongyifu.mms.utils.RYFMapUtil;

public class MerMerchantService {
	
	private MerInfoDao merInfoDao = new MerInfoDao();
	private MerOperDao merOperDao = new MerOperDao();
	private OperAuthDao operAuthDao = new OperAuthDao();
	
//	private String doImportRSAFile(String mid, String fileText) {
//		// C#格式的xml文件
//		if (fileText.charAt(0) == '<' && fileText.charAt(12) == '>') {
//			Document doc = null;
//			try {
//				doc = DocumentHelper.parseText(fileText);
//				Element root = doc.getRootElement();
//				String Modulus = root.element("Modulus").getText();
//				String Exponent = root.element("Exponent").getText();
//				String pubKey = getPublicKey(Modulus, Exponent);
//				int id = merInfoDao.updateMerPubKey(pubKey, mid);
//				if (id != 1) return "操作失败";
//				return AppParam.SUCCESS_FLAG;
//
//			} catch (Exception e) {
//				return "文件内容错误";
//			}
//
//		} else if (fileText.indexOf("<") == -1 && fileText.indexOf(">") == -1) {
//
//			int id = merInfoDao.updateMerPubKey(fileText, mid);
//			if (id != 1) return "操作失败";
//			return AppParam.SUCCESS_FLAG;
//
//		} else {
//			return "上传的文件格式错误";
//		}
//	}
	
//	private String getPublicKey(String modulus, String publicExponent) throws Exception {
//		byte[] m1 = Base64.decode(modulus);
//		byte[] e1 = Base64.decode(publicExponent);
//
//		BigInteger m = new BigInteger(m1);
//		BigInteger e = new BigInteger(e1);
//		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		PublicKey publicKey = keyFactory.generatePublic(keySpec);
//		return Base64.encode(publicKey.getEncoded());
//	}
	
	public String addMerRsaFile(String mid, String rsaFile) {
		if (mid.trim().length()==0|| Ryt.empty(rsaFile)) return "参数输入错误";
		Minfo mer = null;
		try {
//			mer = merInfoDao.getOneMinfo(mid);
			
			mer = getOneMinfo(mid);
		} catch (Exception e) {
			return "商户不存在";
		}
		if (null == mer) return "商户不存在";
		if (!Ryt.empty(mer.getPublicKey())) return "商户已导入RSA公钥";
		
//		return doImportRSAFile(mid, rsaFile.trim());
		
		Map<String, Object> requestParaMap = new HashMap<String, Object>();
		requestParaMap.put("serviceName", "rmi_add_merRasFile");
		requestParaMap.put("mname", mer.getName());
		requestParaMap.put("mid", mid);
		requestParaMap.put("rsaFile", rsaFile.trim());
		
		String resultCode = null;
		try {
			Object object = RypayRmiClient.executeRequest(requestParaMap);
			JSONObject jsonObject = JSONObject.fromObject(object);
			
			String result = jsonObject.getString("result");
			JSONObject jsonObj = JSONObject.fromObject(result);
			resultCode = jsonObj.getString("resultCode");
		} catch (Exception e) {
			return "文件内容错误";
		}
		return resultCode;
	}
	
	/**
	 * 获得单个商户对象
	 * @param mid
	 * @return
	 */
	public Minfo getOneMinfo(String mid) {
		//search_edit_info.jsp中调用
//		Minfo m = null;
//		try {
//			m = merInfoDao.getOneMinfo(mid);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return m;
		
		Map<String, Object> requestParaMap = new HashMap<String, Object>();
		requestParaMap.put("serviceName", "rmi_query_minfo");
		requestParaMap.put("mid", mid);
		Object object = null;
		try {
			object = RypayRmiClient.executeRequest(requestParaMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Minfo) object;
	}
	/**
	 * 查询商户重要信息
	 * @param mid
	 * @return
	 */
	public Minfo getImportentMsgByMid(String mid){
		return merInfoDao.getImportentMsgByMid(mid);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("serviceName", "rmi_query_importMsgByMid");
//		map.put("mid", mid);
//		Object object = null;
//		try {
//			object = RypayRmiClient.executeRequest(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONObject jsonObject = JSONObject.fromObject(object);
//		Object obj = jsonObject.get("minfo");
//		JSONObject jsonObj = JSONObject.fromObject(obj);
//		return  (Minfo) JSONObject.toBean(jsonObj,Minfo.class);
	}
	
	/**
	 * 修改商户重要信息
	 * @param mid
	 * @param minfoName
	 * @param abbrev
	 * @param category
	 * @param codeExpDate
	 * @return
	 */
	public String updateMinfoImportantData(Minfo minfo){
		String mid=minfo.getId();
		if(merInfoDao.isExistMinfoName(minfo.getName(),minfo.getAbbrev(),mid)){
			return "商户简称("+minfo.getAbbrev()+")已经存在。";
		}
		String backStr="修改失败！";
		int row=merOperDao.updateImportantMsg(minfo);
		 
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("serviceName", "rmi_update_importMsg");
//		map.put("minfo", minfo);
//		Object object = null;
//		try {
//			object = RypayRmiClient.executeRequest(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONObject jsonObject = JSONObject.fromObject(object);
//		int row = jsonObject.getInt("row");
		 
		if (row == 1) {
			/* 存管系统不上，先注释*/ 
//			merOperDao.addCgTask( "M", String.valueOf(mid));
			RYFMapUtil.getInstance().refreshMinfoMap(mid);//刷新缓存
			// 刷新ets
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("t", "minfo");
			p.put("k", mid);
			backStr= EWPService.refreshEwpETS(p) ? AppParam.SUCCESS_FLAG : "修改成功，但刷新ewp失败!";
		}
		return backStr;
	}
	
	/**
	 *修改商户基本信息
	 *@param 商户对象
	 *@return 成功或失败信息
	 */
	public String editMinfos(Minfo minfo) {
		//search_edit_info.jsp中调用
		try {
			merInfoDao.editMinfos(minfo);
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败！";
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("t", "minfo");
		p.put("k", minfo.getId());
		return EWPService.refreshEwpETS(p) ? "修改成功!" : "资料修改成功，刷新ewp失败!";
	}
	
	/**
	 * 商户联系人信息增加或者修改
	 * @param minfo
	 * @return
	 */
	public String editMinfoContact(MinfoH minfo) {
		//edit_Minfo.jsp和search_edit_info.jsp中调用
		try {
			int row=merInfoDao.editMinfoContact(minfo);
			return row==1?AppParam.SUCCESS_FLAG:"修改失败";
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
	}
	
	/**
	 *添加商户基本信息增加
	 *@param 商户对象
	 *@return 成功或失败信息
	 */
	public String addMinfos(MinfoH minfo, String modelflg) { //  add_info.jsp中调用
		if (merInfoDao.checkMerName(minfo.getName(), "0", minfo.getAbbrev())) { // 验证商户名是否被占用
			return "商户简称("+minfo.getAbbrev()+")已被占用！";
		}
		String mid;
		try {
			mid =""+merInfoDao.addMinfoBase(minfo);
		} catch (Exception e) {
			e.printStackTrace();
			return "增加失败";
		}

		RYFMapUtil obj = RYFMapUtil.getInstance();
		obj.refreshMinfoMap(mid);

		return String.valueOf(mid);
		//  这里不需要刷新ets
	}
	
	/**
	 * 后台修改用户权限
	 * @param menu
	 * @return
	 */
	public String addMenu(int operId,String menu,String menu_nc){
		String msg = "";
		LoginUser user=operAuthDao.getLoginUser();
		String mid=user.getMid();
		boolean authFlag=MenuService.hasThisAuth(user.getAuth(), 22);//id=22的为操作员权限修改
		if(!authFlag)return "您的权限不足！";
		if(operId==user.getOperId()){
			return "权限分配失败！无法自己给自己分配权限";
		}
		try {
//			int role = operAuthDao.getRole(mid,operId);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serviceName", "rmi_query_role");
			map.put("mid", mid);
			map.put("operId", operId);
			
			Object object = RypayRmiClient.executeRequest(map);
			JSONObject jsonObject = JSONObject.fromObject(object);
			int role = jsonObject.getInt("role");
			
			String authstr ="";
			if (menu.equals("")) {
				authstr = MenuService.genDefaultUserAuth(role);
				msg = "权限已清空！";
//				operAuthDao.addMenu(authstr, mid,operId); 
				
				map.remove("serviceName");
				map.put("serviceName", "rmi_add_menu");
				map.put("authstr", authstr);
				RypayRmiClient.executeRequest(map);
			} else {
				String[] authId = menu.split(",");
//				String authOld=operAuthDao.findAuth(String.valueOf(mid), String.valueOf(operId));
			
				map.remove("serviceName");
				map.put("serviceName", "rmi_query_auth");
				
				Object obj = RypayRmiClient.executeRequest(map);
				JSONObject jsonObj = JSONObject.fromObject(obj);
				String authOld = jsonObj.getString("authOld");
				
//				authstr = MenuService.genUserAuth(authId, role);
				authstr = MenuService.genUserAuth(authId, role, authOld,menu_nc);
				msg = "分配成功！";
//				operAuthDao.addMenu(authstr, mid,operId);
				
				map.remove("serviceName");
				map.put("serviceName", "rmi_add_menu");
				map.put("authstr", authstr);
				RypayRmiClient.executeRequest(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "权限分配失败！";
		}
		return msg;
	}
	
	/**
	 * 根据id查询该操作员现有的权限
	 * @param mid
	 * @param oid
	 * @return
	 */
	public String checkMenu(String mid, int oid) {
		//edit_oper_auth.jsp(jsp/merchant)中调用
		String auth = operAuthDao.checkMenu(mid, oid);
		if (auth != null) {
			return MenuService.queryUserAuthIndex(auth);
		} else {
			return "noAuth";
		}
	}
	
	/**
	 * 根据mid返回对应的融易通操作员的下拉框字符 （新增）
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> showOPers() throws Exception {
		String mid=merOperDao.getLoginUserMid();
//		Map<Integer, String> opers = merOperDao.getHashOper(mid);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceName", "rmi_query_operByMid");
		map.put("mid", mid);
		
		Object object = RypayRmiClient.executeRequest(map);
		
		JSONObject jsonObject = JSONObject.fromObject(object);
		Object obj = jsonObject.get("operMap");
		JSONObject jsonObj = JSONObject.fromObject(obj);
		Map<Integer, String> opers = (Map<Integer, String>) JSONObject.toBean(jsonObj, HashMap.class);
		return opers;
	}
	
	/**
	 * 根据operId查询权限
	 * @param operId
	 * @return
	 * @throws Exception 
	 */
	public List<MenuBean> searchOperAuth(int operId) throws Exception {
		String mid=merOperDao.getLoginUserMid();
		int myid=merOperDao.getLoginUser().getOperId();
//		List<MenuBean> authInfoList = MenuService.getMerMenu(); // 普通商户
		List<MenuBean> authInfoList =new ArrayList<MenuBean>();
		authInfoList=MenuService.getMerMenu2();
			//我的权限字符串
//			String myauthStr=operAuthDao.checkMenu(mid, myid);
			
			Map<String, Object> requestParaMap = new HashMap<String, Object>();
			requestParaMap.put("serviceName", "rmi_check_menu");
			requestParaMap.put("mid", mid);
			requestParaMap.put("operId", myid);
			Object object = RypayRmiClient.executeRequest(requestParaMap);
			
			JSONObject jsonObject = JSONObject.fromObject(object);
			String myauthStr = jsonObject.getString("authStr");
		
			//操作员权限字符串
//			String authStr=operAuthDao.checkMenu(mid, operId);
			requestParaMap.clear();
			requestParaMap.put("serviceName", "rmi_check_menu");
			requestParaMap.put("mid", mid);
			requestParaMap.put("operId", operId);
			Object obj = RypayRmiClient.executeRequest(requestParaMap);
			
			JSONObject jsonObj = JSONObject.fromObject(obj);
			String authStr = jsonObj.getString("authStr");
			
				//判断是否包含我的账户
				if(!(myauthStr.charAt(30)+"").trim().equals("1")){
					if(authInfoList.size()>4){
						authInfoList.remove(4);
					}
				}else if(authInfoList.size()<=4){
					
				}
			setAuthInfo(authStr, authInfoList);
		return authInfoList;
	}
	/**
	 * 设置是否选择（即是否有权限）
	 * @param authArr
	 * @param authInfoList
	 */
	private void setAuthInfo(String authStr,List<MenuBean> authInfoList){
		for (int i = 0; i < authInfoList.size(); i++) {
			MenuBean menuBean=authInfoList.get(i);
			int id=menuBean.getId();
			Boolean checked=MenuService.hasThisAuth(authStr,id);//是否有权限
			authInfoList.get(i).setChecked(checked);
			if(menuBean.getChildren()!=null){
				setAuthInfo(authStr, menuBean.getChildren());
			}
		}
	}
	
	/**
	 * 删除操作员
	 * @param oper_id 操作员ID
	 * @return 返回删除操作的结果
	 */
	public String deleteOperInfo(String mid, int oper_id) {
		//operManage.jsp(jsp/merchant)中调用
		try {
			merOperDao.deleteOper(mid, oper_id);
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		return AppParam.SUCCESS_FLAG;
	}
	
	/**
	 * 查询操作员信息  
	 * @param mid 商户号
	 * @param operName  操作员名
	 * @param pageNo 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CurrentPage<OperInfo> getOpers4Object(String mid, String operName, int pageNo) {
		//search_edit_oper.jsp中调用
//		return merOperDao.getOpers4Object(mid, operName, pageNo);
		Map<String, Object> requestMap=new HashMap<String, Object>();
		requestMap.put("serviceName", "RMI_OPERINFO_SEARCH");
		requestMap.put("mid", mid);
		requestMap.put("page_no", pageNo);
		Object obj;
		CurrentPage<OperInfo> pageDataLst = null;
		try {
			obj = RypayRmiClient.executeRequest(requestMap);
			  JSONObject jsonObject = JSONObject.fromObject(obj);
			  if (jsonObject != null) {
				  int num = jsonObject.getInt("pageNo");
				  int pageSize =jsonObject.getInt("pageSize");
				  int totalRecord =jsonObject.getInt("totalRecord");
				  int pagesAvailable =jsonObject.getInt("pagesAvailable");
				   JSONArray jsonArray = jsonObject.getJSONArray("operInfo");
				   List<OperInfo> operInfoList = (List<OperInfo>)JSONArray.toCollection(jsonArray, OperInfo.class);
				   pageDataLst = new CurrentPage<OperInfo>();
				   pageDataLst.setPageItems(operInfoList);
				   pageDataLst.setPageNumber(num);
				   pageDataLst.setPageSize(pageSize);
				   pageDataLst.setPageTotle(totalRecord);
				   pageDataLst.setPagesAvailable(pagesAvailable);
				  }
		} catch (Exception e) {
			return null;
		}
		  return pageDataLst;
	}
	
	/**
	 * 添加操作员  
	 * @param action 根据此值，决定mtype类型
	 * @param ostate 状态
	 * @param oper_email Email地址
	 * @param oper_tel 电话号码
	 * @param oper_name 操作员名字
	 * @param operpass 密码
	 * @param operid 操作员号
	 * @param minfo_id 商户ID
	 * @param oper_mid 操作员的商户号  
	 * @return 返回增加操作的结果
	 */
	public String addOperInfo(String action, String ostate, String oper_email, String oper_tel, String oper_name,
					String operpass, int operid, String minfo_id,String oper_mid) {
		//operManage.jsp(jsp，admin)中调用
//		if(!"1".equals(minfo_id)&&"1".equals(oper_mid)&&operAuthDao.isExistOper(minfo_id))return "此商户已增加有操作员，不能再添加！";
		
		// 根据商户号查询操作员是否存在
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("serviceName", "rmi_query_oper_is_exist");
		requestMap.put("minfo_id", minfo_id);
		boolean flag = false;
		try {
			Object object = RypayRmiClient.executeRequest(requestMap);
			JSONObject jsonObject = JSONObject.fromObject(object);
			flag = jsonObject.getBoolean("flag");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(!"1".equals(minfo_id)&&"1".equals(oper_mid)&& flag)return "此商户已增加有操作员，不能再添加！";
		
//		if(!"1".equals(oper_mid)&&operAuthDao.queryOperNum(minfo_id)>=4){return "操作员数量已大于等于四个，不能再添加！";};
		
		requestMap.remove("serviceName");
		requestMap.put("serviceName", "rmi_query_oper_num");
		int count = 0;
		try {
			Object object = RypayRmiClient.executeRequest(requestMap);
			JSONObject jsonObject = JSONObject.fromObject(object);
			count = jsonObject.getInt("count");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(!"1".equals(oper_mid)&& count >= 4){return "操作员数量已大于等于四个，不能再添加！";};
		int mtype = 0;
		if (action.equals("credit")) {
			mtype = 1;
		}
		RYFMapUtil obj = RYFMapUtil.getInstance();
		if (!obj.getMerMap().containsKey(minfo_id)) {
			return "该商户不存在";
		};
		
//		if (operAuthDao.hasOper(operid, minfo_id, mtype) > 0) {
		requestMap.remove("serviceName");
		requestMap.put("serviceName", "rmi_query_has_oper");
		requestMap.put("operid", operid);
		requestMap.put("mtype", mtype);
		try {
			Object object = RypayRmiClient.executeRequest(requestMap);
			JSONObject jsonObject = JSONObject.fromObject(object);
			count = jsonObject.getInt("count");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if (count > 0) {
			return "操作员号已被占用，请重新输入";
		}
		int role = 1;
		String auth="";
		if ("1".equals(minfo_id)) {
			role = 0;
		}else if(!"1".equals(minfo_id)&&"1".equals(oper_mid)){
			auth=MenuService.genAllUserAuth("1");
		}else{
			
			for (int i = 1; i < Constant.AUTHLEN; i++) {
				auth=auth+"0";
			}
			auth="1"+auth;
		}
//		try {
//		merOperDao.add(action, ostate, oper_email, oper_tel, oper_name, operpass, operid, minfo_id, mtype, role,auth);
//	} catch (Exception e) {
//		return "操作异常，请重新再试或与管理员联系！";
//	}
//	return AppParam.SUCCESS_FLAG;
		
		//调用接口取值
		requestMap.clear();
		requestMap.put("serviceName", "RMI_OPERINFO_ADD");
		requestMap.put("action", action);
		requestMap.put("ostate", ostate);
		requestMap.put("oper_email", oper_email);
		requestMap.put("oper_tel", oper_tel);
		requestMap.put("oper_name", oper_name);
		requestMap.put("operpass", operpass);
		requestMap.put("operid", operid);
		requestMap.put("minfo_id", minfo_id);
		requestMap.put("mtype", mtype);
		requestMap.put("role", role);
		requestMap.put("auth", auth);
		try {
			RypayRmiClient.executeRequest(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
			return "操作异常，请重新再试或与管理员联系！";
		}
		return AppParam.SUCCESS_FLAG;
	}
	
	/**
	 * 修改操作员信息
	 * @param ostate  状态  0：正常
	 * @param oper_email Email地址
	 * @param oper_tel 电话号码
	 * @param oper_name 操作员姓名
	 * @param operid 操作员号
	 * @param mid  商户号
	 * @param mtype 类型 0
	 * @return
	 */
	public String editOperInfo(String ostate, String oper_email,String oper_tel, String oper_name, String operid, String mid,
			int mtype) {
		// operManage.jsp(jsp/merchant)中调用
		// try {
		// merOperDao.edit(ostate, oper_email, oper_tel, oper_name, operid, mid,
		// mtype);
		// } catch (Exception e) {
		// e.printStackTrace();
		// return "false";
		// }
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("serviceName", "RMI_OPERINFO_UPDATE");
		requestMap.put("state", ostate);
		requestMap.put("operEmail", oper_email);
		requestMap.put("operTel", oper_tel);
		requestMap.put("operName", oper_name);
		requestMap.put("operId", operid);
		requestMap.put("mid", mid);
		requestMap.put("mtype", mtype);
		try {
			RypayRmiClient.executeRequest(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
			return "修改失败";
		}
		  return AppParam.SUCCESS_FLAG;
	}
	
	/**
	 * 操作员修改密码 
	 * @param mid  商户号
	 * @param oper_id 操作员号
	 * @param opass  原密码
	 * @param npass  新密码
	 * @return
	 */
	public String editPass(String mid, String oper_id, String opass,
			String npass) {
		// search_chang_oper_pass.jsp(jsp,admin)中调用
		try {
			int operId = Integer.parseInt(oper_id);
			if (Ryt.empty(npass))
				return "请输入新密码。";
//			String oldPass = merOperDao.getOldPass(0, operId, mid);
			Map<String, Object> Map = new HashMap<String, Object>();
			Map.put("serviceName", "RMI_OPERINFO_SELECT_PASS");
			Map.put("mtype", 0);
			Map.put("operId", oper_id);
			Map.put("mid", mid);
			Object obj =RypayRmiClient.executeRequest(Map);
			JSONObject  json =JSONObject.fromObject(obj);
			String result = json.getString("resultCode");
			if (!result.equals(opass)) {
				return "原密码错误！";
			}
			// return operAuthDao.updateOperPwd(npass, mid, operId, 0) ? "修改成功!": "修改失败";
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("serviceName", "RMI_OPERINFO_UPDATE_PASS");
			requestMap.put("operId", oper_id);
			requestMap.put("mid", mid);
			requestMap.put("opass", opass);
			requestMap.put("npass", npass);
			try {
				RypayRmiClient.executeRequest(requestMap);
			} catch (Exception e) {
				e.printStackTrace();
				return "修改失败";
			}
			return AppParam.SUCCESS_FLAG;
		} catch (Exception e) {
			return "修改失败";
		}
	}
	
	/**
	 * 查询消息通知（查询）
	 * @param notice
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MMSNotice> getMessage(MMSNotice notice) {
		//queryMessage.jsp(jsp/merchant)中调用
		List<MMSNotice> l = null;
		try {
//			notice.setMid(merOperDao.getLoginUser().getMid());
//			l = new SystemDao().getMessage(notice);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serviceName", "rmi_query_mmsnotice");
			map.put("mid", merOperDao.getLoginUser().getMid());
			map.put("begin_date", notice.getBeginDate());
			map.put("end_date", notice.getEndDate());
			
			Object object = RypayRmiClient.executeRequest(map);
			JSONObject jsonObject = JSONObject.fromObject(object);
			JSONArray jsonArray = jsonObject.getJSONArray("mmsNotice");
			l = (List<MMSNotice>)JSONArray.toCollection(jsonArray, MMSNotice.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 操作员日志查询	
	 * @param mid
	 * @param operId
	 * @param sdate
	 * @param edate
	 * @param pageNo
	 * @return
	 */
	public CurrentPage<OperLog> getMidOperLog(String mid, String operId, int sdate, int edate, int pageNo) {
		Map<String, Object> reqPramas = new HashMap<String, Object>();
		reqPramas.put("serviceName", "rmi_search_log");
		reqPramas.put("mid", mid);
		reqPramas.put("operId", operId);
		reqPramas.put("beginDate", sdate);
		reqPramas.put("endDate", edate);
		reqPramas.put("pageNo", pageNo);
			CurrentPage<OperLog> page = null;
			try {
				page = (CurrentPage)RypayRmiClient.executeRequest(reqPramas);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return page;
	}
		
	public FileTransfer downloadLog(String mid, String operId, int sdate, int edate) throws Exception {
		Map<String, Object> reqPramas = new HashMap<String, Object>();
		//reqPramas.put("transCode", "TCI0031");rmi_oper_log
		reqPramas.put("serviceName", "rmi_down_log");
		reqPramas.put("mid", mid);
		reqPramas.put("operId", operId);
		reqPramas.put("beginDate", sdate);
		reqPramas.put("endDate", edate);
		JSONObject resStr;
		resStr = (JSONObject)RypayRmiClient.executeRequest(reqPramas);
		if(!resStr.isEmpty()){
			JSONArray jsonArray11 =resStr.getJSONArray("operLogs");
			List<OperLog> operLogList = (List)JSONArray.toList(jsonArray11, OperLog.class);
		
		    	ArrayList<String[]> list = new ArrayList<String[]>();
		    	list.add("商户号,商户简称,操作员号,操作员名,系统日期,系统时间,操作员IP,操作,操作结果".split(","));
		    	int i = 0;

		    	for (OperLog operLog : operLogList) {
		    		String[] str = {  operLog.getMid() + "", operLog.getName() + "",operLog.getOperId().toString()
		    				, operLog.getOper_name()
		    				+ "",
		    				operLog.getSysDate().toString()
		    				, DateUtil.getStringTime(operLog.getSysTime())+ "",
		    				operLog.getOperIp(),operLog.getAction(),
		    				operLog.getActionDesc() + "" };
		    		i += 1;
		    		list.add(str);
		    	}
		    	String[] str = { "总计:" + i + "条记录", "", "", "", "", "" , "", "", "" };
		    	list.add(str);
		    	String filename = "OPERLOG_" + DateUtil.today() + ".xlsx";
		    	String name = "操作员日志";
		    	return new DownloadFile().downloadXLSXFileBase(list, filename, name);
		    }
		return null;
	}
	
	/**
	 * 根据Id查找 消息通知
	 * @param id
	 * @return
	 */
	public MMSNotice getMessageById(int id) {
		//queryMessage.jsp(jsp/merchant)中调用
		if (id == 0) return null;
		try {
//			return new SystemDao().getMessageById(id);
			
			Map<String, Object> requestParaMap = new HashMap<String, Object>();
			requestParaMap.put("serviceName", "rmi_query_mmsnoticeById");
			requestParaMap.put("id", id);
			
			Object object = RypayRmiClient.executeRequest(requestParaMap);
			JSONObject jsonObject = JSONObject.fromObject(object);
			Object obj = jsonObject.get("mmsNotice");
			JSONObject jsonObj = JSONObject.fromObject(obj);
			return (MMSNotice) JSONObject.toBean(jsonObj,MMSNotice.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 操作手册下载
	 * @return 
	 * @throws Exception 
	 * @throws Exception 
	 */
	public FileTransfer downLoadUserManual() throws Exception{
		 String saveurl =Ryt.getParameter("SettlementFilePath");
		   File file=new File(saveurl+"/operManual.doc");
		   ByteArrayOutputStream  bytebuffer = new ByteArrayOutputStream();
			try {
				InputStream is = new FileInputStream(file);
			   int buff=0;
			   byte[] b = new byte[1024]; 
				while((buff=is.read(b))!=-1){
					  bytebuffer.write(b, 0, buff);
				}
			   is.close();
			} catch (FileNotFoundException e) {
					throw new Exception("找不到你要下载的文件！");
			} catch (IOException e) {
					throw new Exception("io异常，文件读取失败！");
			}
		   FileTransfer filetransfer=new FileTransfer("operManual.doc", "application/doc",bytebuffer.toByteArray());
		   return filetransfer;
	}
//	private List<String> getUploadFiles(String url){
//	File file=new File(url);
//	File[] fileList=file.listFiles();
//	List<String> fileNameList=new ArrayList<String>();
//	for (int i = 0; i < fileList.length; i++) {
//		fileNameList.add(fileList[i].getName());
//	}
//	return fileNameList;
//}
}
