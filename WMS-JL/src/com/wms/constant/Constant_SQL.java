package com.wms.constant;

public interface Constant_SQL {
	String dict_sql_1 = "SELECT type,key,value FROM wms_sys_dictionary";
	String unit_sql_1 = "SELECT \"unitCode\",\"unitName\" FROM \"wms_sys_unitInfo\" ";
	String goods_sql_1 = "SELECT \"goodsID\",\"goodsName\" FROM \"wms_sys_goodsInfo\" GROUP BY \"goodsID\",\"goodsName\" ";
	String user_sql_1 = "SELECT \"userID\",\"userName\",\"unitCode\" FROM \"wms_sys_userInfo\"";
	String menu_sql = "SELECT \"id\",\"parentid\",\"menuName\",\"menuUrl\",\"menuType\",\"menuClass\"  FROM \"wms_sys_menulist\"";
	String goods_sql_2 = "SELECT \"goodsID\",\"goodsName\",\"goodsVersion\" FROM \"wms_sys_goodsInfo\" WHERE \"unitCode\"='";

}
