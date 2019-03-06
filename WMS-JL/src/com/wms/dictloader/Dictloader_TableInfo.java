package com.wms.dictloader;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.wms.constant.Constant_Table;

import common.toSQL.SQLBean.TableColum;


public class Dictloader_TableInfo {
	public static TableColum userColums;//用户表
	public static TableColum goodsColums;//货品表
	public static TableColum storeColums;//仓库表
	public static TableColum unitColums;//单位表
	public static TableColum menuColums;//菜单表
	public static TableColum inventoryColums;//库存表
//	private static TableColum unitColum;
	//初始化字典
	public static void getDictInfo() {
		//连接数据库获取表字段
		userColums = new TableColum(Constant_Table.userInfo);//用户表
		unitColums = new TableColum(Constant_Table.unitInfo);//单位表
		menuColums = new TableColum(Constant_Table.menulist);//菜单表
		goodsColums = new TableColum(Constant_Table.goodsInfo);//货品表
		storeColums = new TableColum(Constant_Table.storeInfo);//仓库表
		inventoryColums = new TableColum(Constant_Table.inventory);//库存表
	}
	//获取表字段
//	public static TableColum getUnitColum() {
//		return unitColum;
//	}
//	public static TableColum getUserColum() {
//		return userColum;
//	}
//	public static TableColum getMenuColum() {
//		return menuColum;
//	}
//	public static TableColum getGoodsColum() {
//		return goodsColum;
//	}
//	public static TableColum getStoreColum() {
//		return storeColum;
//	}
}
class MyTimerTask_Table extends TimerTask{
	private static Logger log = Logger.getLogger(MyTimerTask_Table.class);
	@Override
	public void run() {
		log.info("表字段信息正在更新中.......");
		Dictloader_TableInfo.getDictInfo();
		log.info("表字段信息更新完成！！！！！");
	}
}
