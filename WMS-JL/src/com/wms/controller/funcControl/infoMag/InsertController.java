package com.wms.controller.funcControl.infoMag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.dictloader.Dictionary;
import com.wms.manage.MenuManage;
import com.wms.service.InsertService;

@RequestMapping(value = "/insert",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class InsertController {
	@Autowired
	private InsertService insertser;
	//新增人员
	@RequestMapping("/user")
	@ResponseBody
	public Map<String,String> adduser(
			@RequestParam("userName") String userName,
			@RequestParam("IDcard") String IDcard,
			@RequestParam("phone") String phone,
			@RequestParam("flaginfo") String flaginfo,
			HttpSession session	){
		Map<String,String> map = Dictionary.checkMD5(IDcard+phone,flaginfo);
		if(map == null) {
			String unitCode = session.getAttribute("unitCode") != null ? session.getAttribute("unitCode").toString():"";
			map = insertser.adduser(userName, IDcard,phone,MenuManage.getMenuStr(),unitCode);
		}
		return map;
	}
	//新增货品基本信息
	@RequestMapping("/goodsInfo")
	@ResponseBody
	public Map<String,Object> goodsInfo(
			@RequestParam("goodsName") String goodsName,
			@RequestParam("simpleName") String simpleName,
			@RequestParam("typeName") String typeID,
			@RequestParam("baseUnit") String baseUnit,
			@RequestParam("sizeMax") String sizeMax,
			@RequestParam("sizeMin") String sizeMin,
			@RequestParam("TP") String TP,
			@RequestParam("quality") String quality,
			@RequestParam("goodsSource") String goodsSource,
			@RequestParam("goodsVersion") String goodsVersion,
			@RequestParam("storage") String storage,
			@RequestParam("limitTerm") String limitTerm,
			@RequestParam("remark") String remark	,
			HttpSession session){
		Map<String,Object> goodsmap = new HashMap<String,Object>();
		goodsmap.put("goodsName",goodsName );
		goodsmap.put("simpleName",simpleName );
		goodsmap.put("typeID",typeID );
		goodsmap.put("baseUnit",baseUnit );
		goodsmap.put("sizeMax",sizeMax );
		goodsmap.put("sizeMin",sizeMin );
		goodsmap.put("TP",TP );
		goodsmap.put("quality",quality );
		goodsmap.put("goodsSource",goodsSource );
		goodsmap.put("goodsVersion",goodsVersion );
		goodsmap.put("storage",storage );
		goodsmap.put("limitTerm",limitTerm );
		goodsmap.put("remark",remark );
		Map<String,Object> map = insertser.addgoods(goodsmap,session);
		return map;
	}
	//新增仓库
	@RequestMapping("/store")
	@ResponseBody
	public Map<String,Object> addstore(
			@RequestParam("storeID") String storeID	,
			@RequestParam("storelocation") String storelocation	,
			@RequestParam("storeSize") String storeSize	,
			@RequestParam("positionNum") String positionNum	,
			@RequestParam("managerID") String managerID	,
			@RequestParam("remark") String remark	,
			HttpSession session){
		Map<String,Object> storemap = new HashMap<String,Object>();
		storemap.put("storeID",storeID );
		storemap.put("storelocation",storelocation );
		storemap.put("storeSize",storeSize );
		if("".equals(positionNum)) {
			positionNum = "1";
		}
		storemap.put("positionNum",positionNum );
		storemap.put("managerID",managerID );
		storemap.put("remark",remark );
		Map<String, Object> map = insertser.addstore(storemap,session);
		return map;
	}
	//新增库存
	@RequestMapping("/inventory")
	@ResponseBody
	public Map<String,Object> addinventory(
			@RequestParam("storeID") String storeID	,
			@RequestParam("position") String position	,
			@RequestParam("goodsID") String goodsID	,
			@RequestParam("goodsVersion") String goodsVersion	,
			@RequestParam("batchID") String batchID	,
			@RequestParam("total") String total	,
			@RequestParam("remark") String remark	,
			HttpSession session){
		Map<String,Object> storemap = new HashMap<String,Object>();
		storemap.put("storeID",storeID );
		storemap.put("position",position );
		storemap.put("goodsID",goodsID );
		storemap.put("goodsVersion",goodsVersion );
		storemap.put("batchID",batchID );
		storemap.put("total",total );
		storemap.put("remark",remark );
		Map<String, Object> map = insertser.addinventory(storemap,session);
		return map;
	}
}
