package com.wms.manage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wms.constant.Constant_SQL;

import common.jdbc.CommonDao;

public class MenuManage {
	private static Map<String,Object> fmenuSet;//父级菜单集合
	private static Map<String,Object> tmenuSet;//子菜单集合
	private static String allmenuStr = "";
	//初始化菜单
	public static boolean initMenu() {
		fmenuSet = new LinkedHashMap<String,Object>();
		tmenuSet = new LinkedHashMap<String,Object>();
		List<Object[]> list = CommonDao.getInstance().selectToObj(Constant_SQL.menu_sql);
		if(!list.isEmpty()) {
			Map<String,Object> menuMap;
			for(Object[] obj:list) {
				if("1".equals(obj[5].toString())) {
					menuMap = setMenuMap(obj);
					fmenuSet.put(menuMap.get("menuType").toString(), menuMap);
				}else if("2".equals(obj[5].toString())) {
					Map<String,Object> map = setMenuMap(obj);
					allmenuStr += obj[0].toString()+"-";
					tmenuSet.put(obj[0].toString(), map);
				}
			}
			//测试
//			String[] menuarray = allmenuStr.split("-");
//			String[] strarray = {menuarray[0],menuarray[2],menuarray[6],menuarray[8]};
//			Map<String,Object> maptest = getMenuObj(strarray);
			return true;
		}
		return false;
	}
	
	public static Map<String, Object> getMenuSet(){
		return fmenuSet;
	}
	
	public static void resetTmenuSet() {
		if(!tmenuSet.isEmpty()) {
			for(String key : tmenuSet.keySet()) {
				@SuppressWarnings("unchecked")
				Map<String,Object> menuMap = (Map<String, Object>) tmenuSet.get(key);
				menuMap.put("state", "0");
				tmenuSet.put(key, menuMap);
			}
		}
	}
	
	protected static Map<String,Object> setMenuMap(Object[] obj){
		Map<String,Object> menuMap;
		menuMap = new LinkedHashMap<String,Object>();
		menuMap.put("id", obj[0] == null?"":obj[0].toString());
		menuMap.put("parent", obj[1] == null?"":obj[1].toString());
		menuMap.put("menuName", obj[2] == null?"":obj[2].toString());
		menuMap.put("menuUrl", obj[3] == null?"":obj[3].toString());
		menuMap.put("menuType", obj[4] == null?"":obj[4].toString());
		return menuMap;
	}
	//菜单功能权限获取、字符串生成
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMenuObj(String[] array){
		Map<String,Object> menuMap = new LinkedHashMap<String,Object>();
		for(int i = 0; i < array.length; i++) {
			Map<String,Object> tmenuMap = (Map<String, Object>) tmenuSet.get(array[i]);
			if(!tmenuMap.isEmpty()) {
				String menuType = tmenuMap.get("menuType").toString();
				Map<String,Object> fmenuMap = (Map<String, Object>) menuMap.get(menuType);
				if(fmenuMap == null) {
					fmenuMap = new LinkedHashMap<String,Object>();
					fmenuMap.putAll((Map<? extends String, ? extends Object>) fmenuSet.get(menuType));
				}
				List<Map<String, Object>> tmenulist = (List<Map<String, Object>>) fmenuMap.get("childmenu");
				if(tmenulist == null) {
					tmenulist = new ArrayList<Map<String, Object>>();
				}
				tmenulist.add(tmenuMap);
				fmenuMap.put("childmenu", tmenulist);
				menuMap.put(menuType, fmenuMap);
			}
		}
		return menuMap;
	}
	public static String getMenuStr() {
		return allmenuStr.substring(0, allmenuStr.length()-1);
	}
	//获取单人菜单权限
	public static Map<String,Object> getWorkContent(String[] array) {
		resetTmenuSet();//权限重置
		for(int i = 0; i < array.length; i++) {
			@SuppressWarnings("unchecked")
			Map<String,Object> menuMap = (Map<String, Object>) tmenuSet.get(array[i]);
			menuMap.put("state", "1");
			tmenuSet.put(array[i], menuMap);
		}
		return getMenuObj(getMenuStr().split("-"));
	}
	
}
