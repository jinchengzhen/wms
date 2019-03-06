package com.wms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wms.dictloader.Dictloader_TableInfo;

import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLStatement.DeleteStatement;


@Service
public class DeleteService {
	//删除用户表信息
	public Map<String, Object> deluser(String userID,String unitCode) {
		TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName());
			table.addSimpleExpression("\""+Dictloader_TableInfo.userColums.getSingleColum(0)+"\"", userID);
			table.addSimpleExpression("\""+Dictloader_TableInfo.userColums.getSingleColum(2)+"\"", unitCode);
			DeleteStatement delete = new DeleteStatement(table);
		return delresult(delete);
	}
	//删除货品基础信息
	public Map<String, Object> delgoods(String goodsID,String unitCode) {
		TableBean table = new TableBean(Dictloader_TableInfo.goodsColums.getTableName());
			table.addSimpleExpression("\""+Dictloader_TableInfo.goodsColums.getSingleColum(1)+"\"", goodsID);
			table.addSimpleExpression("\""+Dictloader_TableInfo.goodsColums.getSingleColum(15)+"\"", unitCode);
			DeleteStatement delete = new DeleteStatement(table);
		return delresult(delete);
	}
	//删除库房
	public Map<String, Object> delstore(String storeID,String unitCode) {
		TableBean table = new TableBean(Dictloader_TableInfo.storeColums.getTableName());
			table.addSimpleExpression("\""+Dictloader_TableInfo.storeColums.getSingleColum(0)+"\"", storeID);
			table.addSimpleExpression("\""+Dictloader_TableInfo.storeColums.getSingleColum(6)+"\"", unitCode);
			DeleteStatement delete = new DeleteStatement(table);
		return delresult(delete);
	}
	
	
	
	
	
	
	//删除结果返回
	public Map<String, Object> delresult(DeleteStatement delete){
		Map<String,Object> map = new HashMap<String, Object>();
		if(delete.excute()) {
			map.put("state", "1");
			map.put("message", "删除成功！");
		}else {
			map.put("state", "0");
			map.put("message", "删除失败！");
		}
		return map;
	}

}
