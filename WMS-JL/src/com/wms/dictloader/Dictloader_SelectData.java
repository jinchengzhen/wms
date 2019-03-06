package com.wms.dictloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.constant.Constant_SQL;

import common.jdbc.CommonDao;

public class Dictloader_SelectData {
	private static Logger logger = Logger.getLogger(Dictloader_SelectData.class);
	private static Map<String,List<Map<String,Object>>> selectdata;
	private static List<Object[]> userlist;
	//初始化字典
	public static void getDictInfo() {
		if(!initSelectData()) {
			logger.info("下拉框数据初始化失败！");
		}
	}
	//获取userlist
	public static List<Object[]> getUserList(){
		return userlist;
	}
	
	//初始化下拉框数据（静态字典数据）
	protected static boolean initSelectData() {
		selectdata = new HashMap<String,List<Map<String,Object>>>();
		userlist = CommonDao.getInstance().selectToObj(Constant_SQL.user_sql_1);//初始化用户信息
		List<Object[]> list = CommonDao.getInstance().selectToObj(Constant_SQL.dict_sql_1);
		if(!list.isEmpty()) {
			for(Object[] obj:list) {
				if("select".equals(obj[0].toString())) {
					try {
						//json数据转list方式
						List<Map<String, Object>> listsd = new ObjectMapper().readValue(obj[2].toString(),new TypeReference<List<Map<String, Object>>>() { });
						if(!listsd.isEmpty()) {
							selectdata.put(obj[1].toString(), listsd);
						}
					}  catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	//获取单个下拉框数据
	public static List<Map<String, Object>> getSelectData(String key){
		return selectdata.get(key);
	}
	//获取N个下拉框数据
	public static Map<String,List<Map<String,Object>>> getMultiSelectData(String...keyArray){
		Map<String,List<Map<String,Object>>> result = new HashMap<String,List<Map<String,Object>>>();
		for(String key:keyArray) {
			List<Map<String, Object>> list = getSelectData(key);
			result.put(key, list);
		}
		return result;
	}
	//获取用户信息下拉框
	public static  List<Map<String, Object>> getuserInfo(String unitCode){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(!userlist.isEmpty()) {
			for(Object[] obj:userlist) {
				if(unitCode.equals(obj[2].toString())) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("id", obj[0] == null?"":obj[0].toString());
					map.put("text", obj[1] == null?"":obj[1].toString());
					result.add(map);
				}
			}
		}
		return result;
	}
	//获取货品名称、型号下拉框
	public static Map<String,List<Map<String, Object>>> getgoodsInfo(String unitCode){
		Map<String,List<Map<String, Object>>> result = new HashMap<String,List<Map<String, Object>>>();
		List<Object[]> list = CommonDao.getInstance().selectToObj(Constant_SQL.goods_sql_2+unitCode+"'");
		if(!list.isEmpty()) {
			List<Map<String,Object>> goodslist = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> versionlist = new ArrayList<Map<String,Object>>();
			for(Object[] obj:list) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", obj[0] == null?"":obj[0].toString());
				map.put("text", obj[1] == null?"":obj[1].toString());
				goodslist.add(map);
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("id", obj[2] == null?"":obj[2].toString());
				map2.put("text", obj[2] == null?"":obj[2].toString());
				versionlist.add(map2);
			}
			result.put("goodsName", goodslist);
			result.put("goodsVersion", versionlist);
		}
		return result;
	}
}
class MyTimerTask_SD extends TimerTask{
	private static Logger log = Logger.getLogger(MyTimerTask_SD.class);
	@Override
	public void run() {
		log.info("下拉框数据正在更新中.......");
		Dictloader_SelectData.getDictInfo();
		log.info("下拉框数据更新完成！！！！！");
	}
}
