package com.wms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wms.dictloader.Dictloader_TableInfo;

import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLStatement.UpdateStatement;

@Service
public class UpdateService {
	//修改用户权限
	public Map<String, Object> updateUser(Map<String, Object> expMap, Map<String, Object> valueMap) {
		if(valueMap != null) {
			TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName(), valueMap);
			table.addSimpleExpression(expMap);
			UpdateStatement sql = new UpdateStatement(table);
			return getresult(sql);
		}
		return null;
	}
	
	//审批
	public Map<String, Object> updateApprove(Map<String, Object> expMap, Map<String, Object> valueMap) {
		if(valueMap != null) {
			TableBean table = new TableBean(Dictloader_TableInfo.applyColums.getTableName(), valueMap);
			table.addSimpleExpression(expMap);
			UpdateStatement sql = new UpdateStatement(table);
			return getresult(sql);
		}
		return null;
	}
	
	protected Map<String, Object> getresult(UpdateStatement sql) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(sql.excute()) {
			result.put("state", "1");
			result.put("message", "修改成功！");
		}else {
			result.put("message", "修改失败！");
		}
		return result;
	}


}
