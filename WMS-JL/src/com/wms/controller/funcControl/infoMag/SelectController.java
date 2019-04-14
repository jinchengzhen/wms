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

import com.wms.dictloader.Dictloader_TableInfo;
import com.wms.manage.MenuManage;
import com.wms.service.SelectService;



@RequestMapping(value = "/select",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class SelectController {
	@Autowired
	private SelectService selectser;
	/*----------人员信息----------------*/
	//分页查询
	@RequestMapping("/userlimitpage")
	@ResponseBody
	public Map<String,Object> userlimit(
			@RequestParam("userName") String userName,
			@RequestParam("IDcard") String IDcard,	
			@RequestParam("currpage") String currpage,	
			@RequestParam("pagesize") String pagesize,
			HttpSession session){
		int a = 0,b=0;
		if(!currpage.isEmpty()) {
			a = Integer.parseInt(currpage);
		}
		if(!pagesize.isEmpty()) {
			b = Integer.parseInt(pagesize);
		}
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = selectser.selectUserlimit(userName,IDcard,a,b,unitCode);
		return map;
	}
	//查询单人信息
	@RequestMapping("/userdetail")
	@ResponseBody
	public Map<String,Object> userdetail(@RequestParam("userID") String userID){
		Map<String,Object> map = selectser.selectuser(userID);
		return map;
	}
	//查询菜单权限
	@RequestMapping("/getworkcontent")
	@ResponseBody
	public Map<String,Object> getworkcontent(@RequestParam("userID") String userID){
		Map<String,Object> map = selectser.selectuser(userID);
		if(map.get("state").toString().equals("1")) {//
			@SuppressWarnings("unchecked")
			Map<String,Object> mapk = (Map<String, Object>) map.get("message");
			String workcontent = mapk.get("workcontent").toString();
			return MenuManage.getWorkContent(workcontent.split("-"));
		}
		return null;
	}
	
	/*----------货品信息----------------*/
	@RequestMapping("/goodslimitpage")
	@ResponseBody
	public Map<String,Object> goodslimitpage(
			@RequestParam("goodsID") String goodsID,
			@RequestParam("typeID") String typeID,	
			@RequestParam("goodsSource") String goodsSource,	
			@RequestParam("currpage") String currpage,	
			@RequestParam("pagesize") String pagesize,
			HttpSession session){
		int a = 0,b=0;
		if(!currpage.isEmpty()) {
			a = Integer.parseInt(currpage);
		}
		if(!pagesize.isEmpty()) {
			b = Integer.parseInt(pagesize);
		}
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = selectser.selectGoodslimit(goodsID,typeID,goodsSource,a,b,unitCode);
		return map;
	}
	//查询单人信息
	@RequestMapping("/goodsdetail")
	@ResponseBody
	public Map<String,Object> goodsdetail(@RequestParam("goodsID") String goodsID){
		Map<String,Object> map = selectser.goodsdetail(goodsID);
		return map;
	}
	/*----------仓库信息----------------*/
	//分页查询
	@RequestMapping("/liststore")
	@ResponseBody
	public Map<String,Object> liststore(
			@RequestParam("storeID") String storeID,
			@RequestParam("managerID") String managerID,	
			@RequestParam("currpage") String currpage,	
			@RequestParam("pagesize") String pagesize,
			HttpSession session){
		int a = 0,b=0;
		if(!currpage.isEmpty()) {
			a = Integer.parseInt(currpage);
		}
		if(!pagesize.isEmpty()) {
			b = Integer.parseInt(pagesize);
		}
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = selectser.selectStorelimit(storeID,managerID,a,b,unitCode);
		return map;
	}
	//查询单个仓库
	@RequestMapping("/storedetail")
	@ResponseBody
	public Map<String,Object> storedetail(@RequestParam("userID") String userID){
		Map<String,Object> map = selectser.selectuser(userID);
		return map;
	}
	/*----------仓库库存信息----------------*/
	//分页查询
	@RequestMapping("/inventorylimitpage")
	@ResponseBody
	public Map<String,Object> inventorylimitpage(
			@RequestParam("storeID") String storeID,
			@RequestParam("currpage") String currpage,	
			@RequestParam("pagesize") String pagesize,
			HttpSession session){
		int a = 0,b=0;
		if(!currpage.isEmpty()) {
			a = Integer.parseInt(currpage);
		}
		if(!pagesize.isEmpty()) {
			b = Integer.parseInt(pagesize);
		}
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = selectser.selectInventlimit(storeID, a, b, unitCode);
		return map;
	}
	//查询申请
	@RequestMapping("/applylimitpage")
	@ResponseBody
	public Map<String,Object> applylimit(
			@RequestParam("apply") String applyID,
			@RequestParam("applyType") String applyType,
			@RequestParam("state") String state,	
			@RequestParam("currpage") String currpage,	
			@RequestParam("pagesize") String pagesize,
			HttpSession session){
		int a = 0,b=0;
		if(!currpage.isEmpty()) {
			a = Integer.parseInt(currpage);
		}
		if(!pagesize.isEmpty()) {
			b = Integer.parseInt(pagesize);
		}
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(Dictloader_TableInfo.applyColums.getSingleColumWithQuotes(9), unitCode);
		map.put(Dictloader_TableInfo.applyColums.getSingleColumWithQuotes(1), applyType);
		map.put(Dictloader_TableInfo.applyColums.getSingleColumWithQuotes(0), applyID);
		Map<String,Object> result = selectser.selectApply(map,a,b,state);
		return result;
	}
}
