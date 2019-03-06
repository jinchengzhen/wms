package com.wms.controller.funcControl.infoMag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.dictloader.Dictloader_SelectData;


@RequestMapping(value = "/init",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class InitFuncController {
	private static Logger logger = Logger.getLogger(InitFuncController.class);
	
	@RequestMapping("/goodsData")
	@ResponseBody
	public Map<String,List<Map<String,Object>>> goodsData(HttpSession session){
		logger.info("用户:"+session.getAttribute("userName").toString()+"访问功能 ——货品信息");
		return Dictloader_SelectData.getMultiSelectData("goodsType","goodsSave","goodsSource");
	}
	@RequestMapping("/storeData")
	@ResponseBody
	public Map<String,List<Map<String,Object>>> storeData(HttpSession session){
		Map<String,List<Map<String,Object>>> map = new HashMap<String,List<Map<String,Object>>>();
		map.put("user", Dictloader_SelectData.getuserInfo(session.getAttribute("unitCode").toString()));
		logger.info("用户:"+session.getAttribute("userName").toString()+"访问功能 ——仓库信息");
		return map;
	}
	@RequestMapping("/inventoryData")
	@ResponseBody
	public Map<String,List<Map<String,Object>>> inventoryData(HttpSession session){
		logger.info("用户:"+session.getAttribute("userName").toString()+"访问功能 ——仓库信息");
		return Dictloader_SelectData.getgoodsInfo(session.getAttribute("unitCode").toString());
	}
}
