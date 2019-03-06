package com.wms.constant;

import common.toSQL.SQLElement.TableElement;

public interface Constant_Table {
	TableElement userInfo = new TableElement("wms_sys_userInfo", "u"); 
	TableElement unitInfo = new TableElement("wms_sys_unitInfo", "n"); 
	TableElement dictionary = new TableElement("wms_sys_dictionary", "d"); 
	TableElement menulist = new TableElement("wms_sys_menulist", "m"); 
	TableElement goodsInfo = new TableElement("wms_sys_goodsInfo", "g"); 
	TableElement storeInfo = new TableElement("wms_sys_storeInfo", "s"); 
	TableElement inventory = new TableElement("wms_store_inventory", "i"); 
}
