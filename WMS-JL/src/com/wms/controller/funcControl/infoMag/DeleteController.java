package com.wms.controller.funcControl.infoMag;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.dictloader.Dictionary;
import com.wms.service.DeleteService;

@RequestMapping(value = "/delete",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class DeleteController {
	@Autowired
	private DeleteService delser;
	//删除人员
	@RequestMapping("/user")
	@ResponseBody
	public Map<String,Object> deleteuser(@RequestParam("userID") String userID,HttpSession session){
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = delser.deluser(userID,unitCode);
		return map;
	}
	//删除货品
	@RequestMapping("/goods")
	@ResponseBody
	public Map<String,Object> deletegoods(@RequestParam("goodsID") String goodsID,HttpSession session){
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = delser.delgoods(goodsID,unitCode);
		return map;
	}
	//删除库房
	@RequestMapping("/store")
	@ResponseBody
	public Map<String,Object> deletestore(@RequestParam("storeID") String storeID,HttpSession session){
		String unitCode = session.getAttribute("unitCode").toString();
		Map<String,Object> map = delser.delstore(storeID,unitCode);
		return map;
	}
}
