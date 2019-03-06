package com.wms.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import common.toSQL.SQLParameters.SQLOption;
import common.toSQL.SQLStatement.SelectStatement;
import common.util.RecordHelper;


@Service
public class ExportService {
	
	public Map<String, Object> exportuserXls(String userName, String IDcard,String unitCode, File file,String webPath) {
		Map<String,Object> map = new HashMap<String, Object>();
		TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName(), Dictloader_TableInfo.userColums.getAllColumsName());
		if(userName != null && !"".equals(userName)) {
			//模糊查询姓名
			ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.userColums.getSingleColum(1)+"\"", SQLOption.likeAfter, userName, Relation.AND, false);
			table.addExpression(expression);
		}
		if(IDcard != null && !"".equals(IDcard)) {
			//模糊查询身份证号码
			ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.userColums.getSingleColum(6)+"\"", SQLOption.likeAfter, IDcard, Relation.AND, false);
			table.addExpression(expression);
		}
		unitCode = "'"+unitCode+"'";
		ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.userColums.getSingleColum(2)+"\"", SQLOption.equals, unitCode, Relation.AND, false);
		table.addExpression(expression);
		SelectStatement select = new SelectStatement(table);
		select.setOrderElement_DESC(Dictloader_TableInfo.userColums.getSingleColum_N(11));;
		@SuppressWarnings("unchecked")
		List<Object[]> list =  (List<Object[]>) select.excute(DataType.Object);
		String[] title = {"序号","姓名","性别","年龄","登录帐号","登录密码","单位名称","身份证号","入职时间"};
		List<String[]> listContent = new ArrayList<String[]>();
		if(!list.isEmpty()) {
			int num = 1;
			for(Object[] userobj : list) {
				String[] content = new String[title.length];
				content[0] = ""+(num++);
				content[1] = userobj[1].toString();
				content[2] = userobj[4].toString().equals("1")?"男":"女";
				content[3] = userobj[9].toString();
				content[4] = userobj[0].toString();
				content[5] = userobj[7].toString();
				content[6] = Dictloader_NameID.getUnitName(userobj[2].toString());
				content[7] = userobj[6].toString();
				content[8] = userobj[10].toString();
				listContent.add(content);
			}
		}
		RecordHelper.exportExcel(file, title, listContent);
		map.put("url", webPath);
		//存储路径：D:\src\eclipseSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\项目名称
		return map;
	}

	public Map<String, Object> exportgoodsXls(String goodsID, String typeID,String goodsSource, String unitCode, File file, String webPath) {
		Map<String,Object> map = new HashMap<String, Object>();
		TableBean table = new TableBean(Dictloader_TableInfo.goodsColums.getTableName(), Dictloader_TableInfo.goodsColums.getAllColumsName());
		if(goodsID != null && !"".equals(goodsID)) {
			//模糊查询货品名称
			ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.goodsColums.getSingleColum(0)+"\"", SQLOption.likeAfter, goodsID, Relation.AND, false);
			table.addExpression(expression);
		}
		if(typeID != null && !"".equals(typeID)) {
			//模糊查询货品种类
			ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.goodsColums.getSingleColum(4)+"\"", SQLOption.likeAfter, typeID, Relation.AND, false);
			table.addExpression(expression);
		}
		if(goodsSource != null && !"".equals(goodsSource)) {
			//模糊查询货源
			ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.goodsColums.getSingleColum(8)+"\"", SQLOption.likeAfter, goodsSource, Relation.AND, false);
			table.addExpression(expression);
		}
		unitCode = "'"+unitCode+"'";
		ExpressionElement_Two expression = new ExpressionElement_Two("\""+Dictloader_TableInfo.goodsColums.getSingleColum(15)+"\"", SQLOption.equals, unitCode, Relation.AND, false);
		table.addExpression(expression);
		SelectStatement select = new SelectStatement(table);
		select.setOrderElement_DESC(Dictloader_TableInfo.goodsColums.getSingleColum_N(15));
		@SuppressWarnings("unchecked")
		List<Object[]> list =  (List<Object[]>) select.excute(DataType.Object);
		String[] title = {"序号","货品编号","货品名称","货品类别","货品型号","规格","货源","存储条件","记录人","备注"};
		List<String[]> listContent = new ArrayList<String[]>();
		if(!list.isEmpty()) {
			int num = 1;
			for(Object[] obj : list) {
				String[] content = new String[title.length];
				content[0] = ""+(num++);
				content[1] = obj2String(obj[1]);
				content[2] = obj2String(obj[0]);
				content[3] = obj2String(obj[2]);
				content[4] = obj2String(obj[9]);
				content[5] = tp2String(obj[10]);
				content[6] = Dictloader_NameID.getSourceName(obj2String(obj[8]));
				content[7] = Dictloader_NameID.getGoodsStorageName(obj2String(obj[12]));
				content[8] = Dictloader_NameID.getUserName(obj2String(obj[17]));
				content[9] = obj2String(obj[16]);;
				listContent.add(content);
			}
		}
		RecordHelper.exportExcel(file, title, listContent);
		map.put("url", webPath);
		//存储路径：D:\src\eclipseSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\项目名称
		return map;
	}
	
	protected String obj2String(Object obj) {
		return obj == null ? "":obj.toString();
	}
	protected String tp2String(Object obj) {
		String result = "";
		String tpstr = obj == null ? "":obj.toString();
		String[] tparray = tpstr.split("-");
		ObjectMapper mapper = new ObjectMapper();
		try {
			for(int i = 0;i < tparray.length;i++) {
				@SuppressWarnings("unchecked")
				Map<String,Object> tpmap = mapper.readValue(tparray[i], HashMap.class);
				result += tpmap.get("name").toString()+":"+tpmap.get("data").toString()+tpmap.get("unit").toString()+";";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
