package com.wms.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wms.dictloader.Dictionary;
import com.wms.dictloader.Dictloader_NameID;
import com.wms.dictloader.Dictloader_TableInfo;
import com.wms.manage.MenuManage;

import common.jdbc.CommonDao;
import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLParameters.DataType;
import common.toSQL.SQLStatement.InsertStatement;
import common.toSQL.SQLStatement.SelectStatement;
import common.toSQL.SQLStatement.UpdateStatement;

@Service
public class SystemService {
	//登录验证查询
	public Map<String, String> select(String userID, String password) {
		Map<String,String> map = new HashMap<String, String>();
		TableBean tableA = new TableBean(Dictloader_TableInfo.userColums.getTableName(),Dictloader_TableInfo.userColums.getAllColumsName());
		tableA.addSimpleExpression("\""+Dictloader_TableInfo.userColums.getSingleColum(0)+"\"", userID);//用户id
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>) new SelectStatement(tableA).excute(DataType.Map);
		if(!list.isEmpty()) {
			//判断是否有登录权限
			if(list.get(0).get(Dictloader_TableInfo.userColums.getSingleColum(14)).equals("1")) {
				//判断密码是否正确（区分大小写）
				if(list.get(0).get(Dictloader_TableInfo.userColums.getSingleColum(7)).equals(password)) {
					map.put("state", "1");
					map.put("message", "Iframe.jsp");
					for(int i = 0;i < Dictloader_TableInfo.userColums.getSize();i++) {
						if("unitCode".equals(Dictloader_TableInfo.userColums.getSingleColum(i))) {
							map.put("unitName", Dictloader_NameID.getUnitName(list.get(0).get(Dictloader_TableInfo.userColums.getSingleColum(i)).toString()));
						}
						map.put(Dictloader_TableInfo.userColums.getSingleColum(i), list.get(0).get(Dictloader_TableInfo.userColums.getSingleColum(i)).toString());
					}
				}else {
					map.put("state", "0");
					map.put("message", "密码错误！");
				}
			}else {
				map.put("state", "0");
				map.put("message", "登录被禁止！");
			}
		}else {
			map.put("state", "0");
			map.put("message", "用户ID不存在！");
		}
		return map;
	}
	//修改密码
	public Map<String,String> updatekey(String password,String userID){
		Map<String,String> result = new HashMap<String,String>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("password", password);
		TableBean table = new TableBean(Dictloader_TableInfo.userColums.getTableName(),map);
		table.addSimpleExpression("\"userID\"", userID);
		UpdateStatement update = new UpdateStatement(table);
		if(update.excute()) {
			result.put("state", "1");
			result.put("message", "修改成功！");
		}else {
			result.put("message", "数据库操作失败！");
		}
		return result;
	}
	//注册处理
	@SuppressWarnings("deprecation")
	public Map<String,String> registDeal(String register,String registerIDcard,String registerPhone,String registEmail,String password,String unitProperties,String unitType,String unitName,String location,String address) {
		
		Map<String,String> map = new HashMap<String, String>();
		Map<String,Object> mapA = new HashMap<String, Object>();
		Map<String,Object> mapB = new HashMap<String, Object>();
		String personCode = registerIDcard.substring(0, 2)+Dictionary.getRandom(4)+registerIDcard.substring(14, 18);//身份证前2位+4位随机数+身份证后四位
		String unitCode = registerIDcard.substring(0, 2)+Dictionary.getRandom(6);//身份证前两位+随机6位
		String today = Dictionary.df_DTIME.format(new Date());
		mapA.put("location", location);
		mapA.put("registerIDcard", registerIDcard);
		mapA.put("unitName", unitName);
		mapA.put("register", register);
		TableBean tablefind = new TableBean(Dictloader_TableInfo.unitColums.getTableName(), Dictloader_TableInfo.unitColums.getMultiColum(0));
		tablefind.addSimpleExpression(mapA);
		SelectStatement select = new SelectStatement(tablefind);
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) select.excute(DataType.Object);
		if( list.isEmpty()) {
			mapA.put("id", Dictionary.df_DAY.format(new Date())+Dictionary.getRandom(6));
			mapA.put("unitCode", unitCode);
			mapA.put("address", address);
			mapA.put("registerPhone", registerPhone);
			mapA.put("registerCode", personCode);
			mapA.put("registDate", today);
			mapA.put("unitType", unitType);
			mapA.put("unitProperties", unitProperties);
			mapA.put("registEmail", registEmail);
			TableBean tableA = new TableBean(Dictloader_TableInfo.unitColums.getTableName(), mapA);
			mapB.put("userName", register);
			mapB.put("userID", personCode);
			mapB.put("unitCode", unitCode);
			mapB.put("phone", registerPhone);
			mapB.put("sex", Integer.parseInt(registerIDcard.substring(16, 17))%2 == 0 ? 2:1);
			try {
				String birthstr = registerIDcard.substring(6, 14);
				String birth = birthstr.substring(0, 4)+"-"+birthstr.substring(4, 6)+"-"+birthstr.substring(6, 8);
				mapA.put("birthday", birth);
				mapA.put("userAge", (new Date().getYear() - Dictionary.df_DTIME.parse(birth+" 00:00:00").getYear()));
			} catch (ParseException e) {
			}
			mapB.put("IDcard", registerIDcard);
			mapB.put("password", password);
			mapB.put("duty", "1");
			mapB.put("hireDate", today);
			mapB.put("updateDate", today);
			mapB.put("workcontent", MenuManage.getMenuStr());
			mapB.put("email", registEmail);
			mapB.put("userAut", "1");
			TableBean tableB = new TableBean(Dictloader_TableInfo.userColums.getTableName(), mapB);
			InsertStatement insertunit = new InsertStatement(tableA);
			InsertStatement insertuser = new InsertStatement(tableB);
			boolean flag = CommonDao.getInstance().routineExcute(insertunit.toSQLStatement(),insertuser.toSQLStatement());
			if(flag) {
				map.put("state", "1");
				map.put("userID", personCode);
				Dictionary.flushDict_SD_NI();
			}else {
				map.put("state", "0");
				map.put("message", "注册失败！");
			}
		}else {
			map.put("state", "0");
			map.put("message", "注册失败，信息已存在！");
		}
		return map;
	}

}
