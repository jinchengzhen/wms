package com.wms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.dictloader.Dictloader_NameID;
import com.wms.dictloader.Dictloader_TableInfo;

import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLElement.ExpressionElement_Two;
import common.toSQL.SQLParameters.DataType;
import common.toSQL.SQLParameters.Relation;
import common.toSQL.SQLParameters.SQLMethods;
import common.toSQL.SQLParameters.SQLOption;
import common.toSQL.SQLStatement.SelectStatement;
import common.util.StringUtil;



@Service
public class SelectService {
	
	
	public Map<String, Object> selectUserlimit(String userName, String IDcard, int currpage, int pagesize, String unitCode) {
		//分页查询
		
		TableBean tabletotal = new TableBean(Dictloader_TableInfo.userColums.getTableName());
		tabletotal.addColum(SQLMethods.COUNT, Dictloader_TableInfo.userColums.getSingleColum(0));
		TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName(), Dictloader_TableInfo.userColums.getMultiColum(0,1,6,3,14));
		if(userName != null && !"".equals(userName)) {
			//模糊查询姓名
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.userColums.getSingleColumWithQuotes(1), SQLOption.likeAfter, userName, Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
		}
		if(IDcard != null && !"".equals(IDcard)) {
			//模糊查询身份证号码
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.userColums.getSingleColumWithQuotes(6), SQLOption.likeAfter, IDcard, Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
		}
		unitCode = "'"+unitCode+"'";
		ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.userColums.getSingleColumWithQuotes(2), SQLOption.equals, unitCode, Relation.AND, false);
		tabletotal.addExpression(expression);
		table.addExpression(expression);
		SelectStatement selecttotal = new SelectStatement(tabletotal);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement((currpage-1)*pagesize, pagesize);
		select.setOrderElement_DESC(Dictloader_TableInfo.userColums.getSingleColum_N(11));
		return limitpage(selecttotal, select);
	}

	//单条查询
	public Map<String, Object> selectuser(String userID) {
		TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName(), Dictloader_TableInfo.userColums.getAllColumsName());
		table.addSimpleExpression(Dictloader_TableInfo.userColums.getSingleColumWithQuotes(0), userID);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement(0, 1);
		return singleSelect(select);
	}
	
	
	
	/*-------------货品信息查询-----------------*/
	public Map<String, Object> selectGoodslimit(String goodsID, String typeID, String goodsSource, int currpage, int pagesize,
			String unitCode) {
			TableBean tabletotal = new TableBean(Dictloader_TableInfo.goodsColums.getTableName());
			tabletotal.addColum(SQLMethods.COUNT, Dictloader_TableInfo.goodsColums.getSingleColum(0));
			TableBean table = new TableBean(Dictloader_TableInfo.goodsColums.getTableName(), Dictloader_TableInfo.goodsColums.getMultiColum(0,1,2,8,9,17));
			if(goodsID != null && !"".equals(goodsID)) {
				//模糊查询货品名称
				ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.goodsColums.getSingleColumWithQuotes(0), SQLOption.likeAfter, goodsID, Relation.AND, false);
				tabletotal.addExpression(expression);
				table.addExpression(expression);
			}
			if(typeID != null && !"".equals(typeID)) {
				//模糊查询货品种类
				ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.goodsColums.getSingleColumWithQuotes(4), SQLOption.likeAfter, typeID, Relation.AND, false);
				tabletotal.addExpression(expression);
				table.addExpression(expression);
			}
			if(goodsSource != null && !"".equals(goodsSource)) {
				//模糊查询货源
				ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.goodsColums.getSingleColumWithQuotes(8), SQLOption.likeAfter, goodsSource, Relation.AND, false);
				tabletotal.addExpression(expression);
				table.addExpression(expression);
			}
			unitCode = "'"+unitCode+"'";
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.goodsColums.getSingleColumWithQuotes(15), SQLOption.equals, unitCode, Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
			SelectStatement selecttotal = new SelectStatement(tabletotal);
			SelectStatement select = new SelectStatement(table);
			select.setLimitElement((currpage-1)*pagesize, pagesize);
			select.setOrderElement_DESC(Dictloader_TableInfo.goodsColums.getSingleColum_N(15));
			Map<String,Object> map = limitpage(selecttotal, select);
			if(map.get("totaldata") != null) {
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> listb = (List<Map<String, Object>>) map.get("message");
				for(int i = 0; i < listb.size();i++) {
					String source = listb.get(i).get("goodsSource").toString();
					String userid = listb.get(i).get("userID").toString();
					listb.get(i).put("goodsSource", Dictloader_NameID.getSourceName(source));
					listb.get(i).put("userID", Dictloader_NameID.getUserName(userid));
				}
				map.put("message", listb);
			}
			return map;
	}
	public Map<String, Object> goodsdetail(String goodsID) {
		Map<String,Object> map = new HashMap<String, Object>();
		TableBean table = new TableBean(Dictloader_TableInfo.goodsColums.getTableName(), Dictloader_TableInfo.goodsColums.getAllColumsName());
		table.addSimpleExpression(Dictloader_TableInfo.goodsColums.getSingleColumWithQuotes(1), goodsID);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement(0, 1);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String, Object>>) select.excute(DataType.Map);
		if(!list.isEmpty()) {
			//iD-Name转换
			String tp = list.get(0).get("TP").toString();
			String[] tparray = tp.split("-");
			ObjectMapper mapper = new ObjectMapper();
			String tpstr = "";
			try {
				for(int i = 0;i < tparray.length;i++) {
					@SuppressWarnings("unchecked")
					Map<String,Object> tpmap = mapper.readValue(tparray[i], HashMap.class);
					tpstr += tpmap.get("name").toString()+":"+tpmap.get("data").toString()+tpmap.get("unit").toString()+";";
				}
				list.get(0).put("TP", tpstr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String storage = list.get(0).get("storage").toString();
			list.get(0).put("storage",Dictloader_NameID.getGoodsStorageName(storage));
			String goodsSource = list.get(0).get("goodsSource").toString();
			list.get(0).put("goodsSource",Dictloader_NameID.getSourceName(goodsSource));
			String limitTerm = list.get(0).get("limitTerm").toString();
			int term = Integer.parseInt(limitTerm);
			int termR = term%1000;
			String dataunit = "";
			switch(term/1000) {
				case 0:
					dataunit = "天";
					break;
				case 1:
					dataunit = "月";
					break;
				case 2:
					dataunit = "年";
					break;
				default:
					break;
			}
			list.get(0).put("limitTerm", termR + dataunit);
			map.put("state", "1");
			map.put("message", list.get(0));
		}else {
			map.put("message", "查询信息出现错误！");
		}
		return map;
	}
	
	public Map<String, Object> selectStorelimit(String storeID, String managerID, int currpage, int pagesize, String unitCode) {
		TableBean tabletotal = new TableBean(Dictloader_TableInfo.storeColums.getTableName());
		tabletotal.addColum(SQLMethods.COUNT, Dictloader_TableInfo.storeColums.getSingleColum(0));
		TableBean table = new TableBean(Dictloader_TableInfo.storeColums.getTableName(), Dictloader_TableInfo.storeColums.getMultiColum(0,1,2,3,4,7));
		if(storeID != null && !"".equals(storeID)) {
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.storeColums.getSingleColumWithQuotes(0), SQLOption.equals, StringUtil.ToPGValString(storeID), Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
		}
		if(managerID != null && !"".equals(managerID)) {
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.storeColums.getSingleColumWithQuotes(4), SQLOption.equals, StringUtil.ToPGValString(managerID), Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
		}
		ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.storeColums.getSingleColumWithQuotes(6), SQLOption.equals, StringUtil.ToPGValString(unitCode), Relation.AND, false);
		tabletotal.addExpression(expression);
		table.addExpression(expression);
		SelectStatement selecttotal = new SelectStatement(tabletotal);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement((currpage-1)*pagesize, pagesize);
		select.setOrderElement_DESC(Dictloader_TableInfo.storeColums.getSingleColum_N(0));
		Map<String,Object> map = limitpage(selecttotal, select);
		if(map.get("totaldata") != null) {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> listb = (List<Map<String, Object>>) map.get("message");
			for(int i = 0; i < listb.size();i++) {
				String manager = listb.get(i).get("managerID").toString();
				listb.get(i).put("managerID", Dictloader_NameID.getUserName(manager));
			}
			map.put("message", listb);
		}
		return map;
	}
	
	//库存查询
	public Map<String, Object> selectInventlimit(String storeID, int currpage, int pagesize, String unitCode) {
		TableBean tabletotal = new TableBean(Dictloader_TableInfo.inventoryColums.getTableName());
		tabletotal.addColum(SQLMethods.COUNT, Dictloader_TableInfo.inventoryColums.getSingleColum(0));
		TableBean table = new TableBean(Dictloader_TableInfo.inventoryColums.getTableName(), Dictloader_TableInfo.inventoryColums.getMultiColum(0,1,2,3,4,7));
		if(storeID != null && !"".equals(storeID)) {
			//模糊查询库房编号
			ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.inventoryColums.getSingleColumWithQuotes(0), SQLOption.likeAfter, storeID, Relation.AND, false);
			tabletotal.addExpression(expression);
			table.addExpression(expression);
		}
		
		unitCode = "'"+unitCode+"'";
		ExpressionElement_Two expression = new ExpressionElement_Two(Dictloader_TableInfo.inventoryColums.getSingleColumWithQuotes(9), SQLOption.equals, unitCode, Relation.AND, false);
		tabletotal.addExpression(expression);
		table.addExpression(expression);
		
		SelectStatement selecttotal = new SelectStatement(tabletotal);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement((currpage-1)*pagesize, pagesize);
		select.setOrderElement_DESC(Dictloader_TableInfo.storeColums.getSingleColum_N(0));
		Map<String,Object> map = limitpage(selecttotal, select);
		if(map.get("totaldata") != null) {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> listb = (List<Map<String, Object>>) map.get("message");
			for(int i = 0; i < listb.size();i++) {
				String manager = listb.get(i).get("managerID").toString();
				listb.get(i).put("managerID", Dictloader_NameID.getUserName(manager));
			}
			map.put("message", listb);
		}
		return map;
	}
	
	//申请
	public Map<String, Object> selectApply(Map<String,Object> map, int currpage, int pagesize,String state) {
		TableBean tabletotal = new TableBean(Dictloader_TableInfo.applyColums.getTableName());
		tabletotal.addColum(SQLMethods.COUNT, Dictloader_TableInfo.applyColums.getSingleColum(0));
		TableBean table = new TableBean(Dictloader_TableInfo.applyColums.getTableName(), Dictloader_TableInfo.applyColums.getMultiColum(0,2,3,4,5,6,7,8));
		if(!"all".equals(state)) {
			if("todo".equals(state)) {
				tabletotal.addSimpleExpression(Dictloader_TableInfo.applyColums.getSingleColum(4), "0");
				table.addSimpleExpression(Dictloader_TableInfo.applyColums.getSingleColum(4), "0");
			}else if("done".equals(state)) {
				tabletotal.addExpression(new ExpressionElement_Two(Dictloader_TableInfo.applyColums.getSingleColumWithQuotes(4), SQLOption.notequal, "'0'",Relation.AND, false));
				table.addExpression(new ExpressionElement_Two(Dictloader_TableInfo.applyColums.getSingleColumWithQuotes(4), SQLOption.notequal, "'0'",Relation.AND, false));
			}
		}
		tabletotal.addSimpleExpression(map);
		table.addSimpleExpression(map);
		
		SelectStatement selecttotal = new SelectStatement(tabletotal);
		SelectStatement select = new SelectStatement(table);
		select.setLimitElement((currpage-1)*pagesize, pagesize);
		select.setOrderElement_DESC(Dictloader_TableInfo.applyColums.getSingleColum_N(6));
		Map<String,Object> result = limitpage(selecttotal, select);
		if(result.get("totaldata") != null) {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> listb = (List<Map<String, Object>>) result.get("message");
			for(int i = 0; i < listb.size();i++) {
				//ID转名称
				String applyer = listb.get(i).get("applyID").toString();
				listb.get(i).put("applyID", Dictloader_NameID.getUserName(applyer));
				String approver = listb.get(i).get("approveID").toString();
				listb.get(i).put("approveID", Dictloader_NameID.getUserName(approver));
			}
			result.put("message", listb);
		}
		return result;
	}
	
	
	/*----------------------------------公共---------------------------------------*/
	
	//查询结果判断返回map（公共）
	public Map<String,Object> limitpage(SelectStatement selecttotal, SelectStatement select){
		Map<String,Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<Object[]> lista = (List<Object[]>) selecttotal.excute(DataType.Object);
		if(!lista.isEmpty()) {
			if(!"0".equals(lista.get(0)[0].toString())) {
				map.put("totaldata", lista.get(0)[0]);
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> listb = (List<Map<String, Object>>) select.excute(DataType.Map);
				if(!listb.isEmpty()) {
					map.put("message", listb);
				}
			}else {
				map.put("message", "未查到相关信息！");
			}
		}else {
			map.put("message", "未查到相关信息！");
		}
		return map;
	}
	
	
	
	public Map<String, Object> singleSelect(SelectStatement select){
		Map<String,Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String, Object>>) select.excute(DataType.Map);
		if(!list.isEmpty()) {
			map.put("state", "1");
			map.put("message", list.get(0));
		}else {
			map.put("message", "查询信息出现错误！");
		}
		return map;
	}

	

	

	
}
