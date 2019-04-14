package com.wms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.wms.dictloader.Dictionary;
import com.wms.dictloader.Dictloader_NameID;
import com.wms.dictloader.Dictloader_TableInfo;

import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLParameters.DataType;
import common.toSQL.SQLStatement.InsertStatement;
import common.toSQL.SQLStatement.SelectStatement;
import common.util.FirstLetterUtil;
import common.util.GenerateDataUtil;



@Service
public class InsertService {
	
	//新增用户
	public Map<String, String> adduser(String userName, String IDcard, String phone, String workcontent, String unitCode) {
		Map<String,String> map = new HashMap<String, String>();
		Map<String,Object> mapA = new HashMap<String, Object>();
		String personCode = GenerateDataUtil.toPersonCode(IDcard);
		String today = Dictionary.df_DTIME.format(new Date());
		mapA.put("userName", userName);
		mapA.put("userID", personCode);
		mapA.put("unitCode", unitCode);
		mapA.put("phone", phone);
		mapA.put("sex", GenerateDataUtil.toSex(IDcard));
		String birth = GenerateDataUtil.toBirth(IDcard);
		mapA.put("birthday", birth);
		mapA.put("userAge", GenerateDataUtil.calculateAge(birth));
		mapA.put("duty", "2");
		mapA.put("IDcard", IDcard);
		String password = ""+Dictionary.getRandom(6);
		mapA.put("password", password);
		mapA.put("hireDate", today);
		mapA.put("updateDate", today);
		mapA.put("workcontent", workcontent);
		mapA.put("email", "");
		mapA.put("userAut", "1");
		TableBean tableA = new TableBean(Dictloader_TableInfo.userColums.getTableName(), mapA);
		InsertStatement insert = new InsertStatement(tableA);
		if(insert.excute()) {
			map.put("state", "1");
			map.put("message", "新增成功！\r新增账号为："+personCode+"\r密码为："+password);
		}else {
			map.put("state", "0");
			map.put("message", "新增失败！");
		}
		return map;
	}
	
	
	//新增货品
	public Map<String, Object> addgoods(Map<String, Object> goodsmap, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		TableBean tablecheck = new TableBean(Dictloader_TableInfo.goodsColums.getTableName(), Dictloader_TableInfo.goodsColums.getMultiColum(0));
		tablecheck.addSimpleExpression(Dictloader_TableInfo.goodsColums.getSingleColum(0), goodsmap.get("goodsName").toString());
		tablecheck.addSimpleExpression(Dictloader_TableInfo.goodsColums.getSingleColum(9), goodsmap.get("goodsVersion").toString());
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) new SelectStatement(tablecheck).excute(DataType.Object);
		if(!list.isEmpty()) {
			map.put("message", "新增失败！该货品信息已存在！");
		}else {
			String unitCode = session.getAttribute("unitCode").toString();
			String userID = session.getAttribute("userID").toString();
			String typeID = goodsmap.get("typeID").toString();
			goodsmap.put("goodsID", FirstLetterUtil.getFirstLetter(goodsmap.get("goodsName").toString().substring(0, 2))+Dictionary.getRandom(5)+System.currentTimeMillis()%10000+unitCode.substring(3,8));
			goodsmap.put("typeName", Dictloader_NameID.getGoodsTypeName(typeID));
			goodsmap.put("state", "1");
			goodsmap.put("unitCode", unitCode);
			goodsmap.put("userID", userID);
			goodsmap.put("updateDate", Dictionary.df_DTIME.format(new Date()));
			TableBean table = new TableBean(Dictloader_TableInfo.goodsColums.getTableName(), goodsmap);
			map = insertresult(new InsertStatement(table));
		}
		return map;
	}
	//新增库房
	public Map<String, Object> addstore(Map<String, Object> storemap, HttpSession session) {
		Map<String,Object> map = new HashMap<String, Object>();
		String unitCode = session.getAttribute("unitCode").toString();//获取单位编号
		TableBean tablecheck = new TableBean(Dictloader_TableInfo.storeColums.getTableName(), Dictloader_TableInfo.storeColums.getMultiColum(1));
		tablecheck.addSimpleExpression("\""+Dictloader_TableInfo.storeColums.getSingleColum(0)+"\"", storemap.get("storeID").toString());
		tablecheck.addSimpleExpression("\""+Dictloader_TableInfo.storeColums.getSingleColum(6)+"\"", unitCode);
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) new SelectStatement(tablecheck).excute(DataType.Object);
		if(!list.isEmpty()) {
			map.put("message", "新增失败！数据异常！请联系管理员");
		}else {
			storemap.put("unitCode", unitCode);
			TableBean table = new TableBean(Dictloader_TableInfo.storeColums.getTableName(), storemap);
			map = insertresult(new InsertStatement(table));
		}
		return map;
	}

	//新增库存
	public Map<String, Object> addinventory(Map<String, Object> storemap, HttpSession session) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sku = GenerateDataUtil.toSKU(storemap);
		String unitCode = session.getAttribute("unitCode").toString();//获取单位编号
		String userID = session.getAttribute("userID").toString();//获取单位编号
		TableBean tablecheck = new TableBean(Dictloader_TableInfo.inventoryColums.getTableName(), Dictloader_TableInfo.storeColums.getMultiColum(1));
		tablecheck.addSimpleExpression("\""+Dictloader_TableInfo.inventoryColums.getSingleColum(0)+"\"", storemap.get("storeID").toString());
		tablecheck.addSimpleExpression("\""+Dictloader_TableInfo.inventoryColums.getSingleColum(1)+"\"", sku);
		tablecheck.addSimpleExpression("\""+Dictloader_TableInfo.inventoryColums.getSingleColum(9)+"\"", unitCode);
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) new SelectStatement(tablecheck).excute(DataType.Object);
		if(!list.isEmpty()) {
			map.put("message", "新增失败！数据异常！请联系管理员");
		}else {
			storemap.put("unitCode", unitCode);
			storemap.put("userID", userID);
			storemap.put("SKU", sku);
			storemap.put("checkDate", Dictionary.df_DAY.format(new Date()));
			storemap.put("creatDate", Dictionary.df_DAY.format(new Date()));
			TableBean table = new TableBean(Dictloader_TableInfo.inventoryColums.getTableName(), storemap);
			map = insertresult(new InsertStatement(table));
		}
		return map;
	}
	
	//新增申请
	public Map<String, Object> addapply(Map<String, Object> map, HttpSession session) {
		String applyID = session.getAttribute("userID").toString();
		map.put("state", "0");
		map.put("unitCode", session.getAttribute("unitCode").toString());
		map.put("applyID", applyID);
		map.put("applydate", Dictionary.df_DAY.format(new Date()));
		map.put("applyNumber", Dictionary.getRandom(6)+(System.currentTimeMillis()%1000000));
		TableBean table = new TableBean(Dictloader_TableInfo.applyColums.getTableName(), map);
		return insertresult(new InsertStatement(table));
	}
	
	//公共方法
	//新增结果返回
	public Map<String, Object> insertresult(InsertStatement insert){
		Map<String,Object> map = new HashMap<String, Object>();
		if(insert.excute()) {
			map.put("state", "1");
			map.put("message", "新增成功！");
		}else {
			map.put("state", "0");
			map.put("message", "新增失败！");
		}
		return map;
	}


}
