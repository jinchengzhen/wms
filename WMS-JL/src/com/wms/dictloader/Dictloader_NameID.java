package com.wms.dictloader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.wms.constant.Constant_SQL;

import common.jdbc.CommonDao;

public class Dictloader_NameID {
	private static Map<String,String> userID2Name;//用户ID转名称
	private static Map<String,String> userName2ID;//用户名称转ID
	private static Map<String,String> unitID2Name;//单位编码转名称
	private static Map<String,String> unitName2ID;//单位名称转编码
	private static Map<String,String> goodsID2Name;//货品名称转编码
	private static Map<String,String> goodsName2ID;//货品编码转名称
	private static Map<String,String> goodsSourceID2Name;//货源编码转名称
	private static Map<String,String> goodsSourceName2ID;//货源编码转名称
	private static Map<String,String> goodsTypeName2ID;//货品类别编码转名称
	private static Map<String,String> goodsTypeID2Name;//货品类别编码转名称
	private static Map<String,String> goodsSaveName2ID;//货品存储条件编码转名称
	private static Map<String,String> goodsSaveID2Name;//货品存储条件编码转名称
	//初始化字典
	public static void getDictInfo() {
		//用户名称-ID
		userID2Name = new HashMap<String,String>();
		userName2ID = new HashMap<String,String>();
		List<Object[]> userlist = Dictloader_SelectData.getUserList();
		if(!userlist.isEmpty()) {
			for(Object[] obj:userlist) {
				userID2Name.put(obj[0].toString(), obj[1].toString());
				userName2ID.put(obj[1].toString(), obj[0].toString());
			}
		}
		//单位id和名称
		unitID2Name = new HashMap<String, String>();
		unitName2ID = new HashMap<String, String>();
		List<Object[]> unitlist = CommonDao.getInstance().selectToObj(Constant_SQL.unit_sql_1);
		if(!unitlist.isEmpty()) {
			for(Object[] obj : unitlist) {
				unitID2Name.put(obj[0].toString(), obj[1].toString());
				unitName2ID.put(obj[1].toString(), obj[0].toString());
			}
		}
		//货品名称-ID
		goodsID2Name = new HashMap<String, String>();
		goodsName2ID = new HashMap<String, String>();
		List<Object[]> goodslist = CommonDao.getInstance().selectToObj(Constant_SQL.goods_sql_1);
		if(!goodslist.isEmpty()) {
			for(Object[] obj:goodslist) {
				goodsID2Name.put(obj[0].toString(), obj[1].toString());
				goodsName2ID.put(obj[1].toString(), obj[0].toString());
			}
		}
		//货品类别编码-ID
		goodsTypeName2ID = new HashMap<String,String>();
		goodsTypeID2Name = new HashMap<String,String>();
		List<Map<String, Object>> goodsTypelist = Dictloader_SelectData.getSelectData("goodsType");
		if(!goodsTypelist.isEmpty()) {
			for(Map<String, Object> map : goodsTypelist) {
				goodsTypeID2Name.put(map.get("id").toString(), map.get("text").toString());
				goodsTypeName2ID.put(map.get("text").toString(), map.get("id").toString());
			}
		}
		//货源编码-名称
		goodsSourceID2Name = new HashMap<String,String>();
		goodsSourceName2ID = new HashMap<String,String>();
		List<Map<String, Object>> goodsSourcelist = Dictloader_SelectData.getSelectData("goodsSource");
		if(!goodsSourcelist.isEmpty()) {
			for(Map<String, Object> map : goodsSourcelist) {
				goodsSourceID2Name.put(map.get("id").toString(), map.get("text").toString());
				goodsSourceName2ID.put(map.get("text").toString(), map.get("id").toString());
			}
		}
		//货品存储条件编码-名称
		goodsSaveName2ID = new HashMap<String,String>();
		goodsSaveID2Name = new HashMap<String,String>();
		List<Map<String, Object>> goodsSavelist = Dictloader_SelectData.getSelectData("goodsSave");
		if(!goodsSavelist.isEmpty()) {
			for(Map<String, Object> map : goodsSavelist) {
				goodsSaveID2Name.put(map.get("id").toString(), map.get("text").toString());
				goodsSaveName2ID.put(map.get("text").toString(), map.get("id").toString());
			}
		}
		
	}
	/*------------------------------初始化信息结束-----------------------------------------------------------------*/
	
	//用户ID-名称
	public static String getUserName(String id) {
		String result = userID2Name.get(id);
		if(result == null) {
			return "";
		}
		return result;
	}
	public static String getUserID(String name) {
		String result = goodsTypeName2ID.get(name);
		if(result == null) {
			return "";
		}
		return result;
	}
	//货品名称-ID转换
	public static String getGoodsName(String id) {
		String result = goodsID2Name.get(id);
		if(result == null) {
			return "";
		}
		return result;
	}
	public static String getGoodsID(String name) {
		String result = goodsName2ID.get(name);
		if(result == null) {
			return "";
		}
		return result;
	}
	//货品类别名称-ID转换
	public static String getGoodsTypeName(String id) {
		String result = goodsTypeID2Name.get(id);
		if(result == null) {
			return "";
		}
		return result;
	}
	public static String getGoodsTypeID(String name) {
		String result = goodsTypeName2ID.get(name);
		if(result == null) {
			return "";
		}
		return result;
	}
	//货品存储条件名称-ID转换
	public static String getGoodsStorageName(String id) {
		String result = goodsSaveID2Name.get(id);
		if(result == null) {
			return "";
		}
		return result;
	}
	public static String getGoodsStorageID(String name) {
		String result = goodsSaveName2ID.get(name);
		if(result == null) {
			return "";
		}
		return result;
	}
	//货源编码-名称转换
	public static String getSourceName(String id) {
		String result = goodsSourceID2Name.get(id);
		if(result == null) {
			return "";
		}
		return result;
	}
	public static String getSourceID(String name) {
		String result = goodsSourceName2ID.get(name);
		if(result == null) {
			return "";
		}
		return result;
	}
	//获取单位名称-编号
	public static String getUnitName(String unitCode) {
		String result = unitID2Name.get(unitCode);
		if(result.isEmpty()) {
			return "";
		}
		return result;
	}
	public static String getUnitCode(String unitName) {
		String result = unitName2ID.get(unitName);
		if(result.isEmpty()) {
			return "";
		}
		return result;
	}
	
}
class MyTimerTask_NI extends TimerTask{
	private static Logger log = Logger.getLogger(MyTimerTask_NI.class);
	@Override
	public void run() {
		log.info("Name-ID信息正在更新中.......");
		Dictloader_NameID.getDictInfo();
		log.info("Name-ID信息更新完成！！！！！");
	}
}