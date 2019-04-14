package com.wms.controller.funcControl.infoMag;

import java.util.Date;
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
import com.wms.service.UpdateService;

@RequestMapping(value = "/update",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class UpdateController {
	@Autowired
	private UpdateService updateser;
	
	//修改用户个人资料
	//修改信息
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String,Object> updateUser(
			@RequestParam("userName") String userName,
			@RequestParam("sex") String sex,
			@RequestParam("birthday") String birthday,
			@RequestParam("IDcard") String IDcard,
			@RequestParam("phone") String phone,
			@RequestParam("userAge") String userAge,
			@RequestParam("hireDate") String hireDate,
			@RequestParam("workcontent") String workcontent	,
			HttpSession session){
		Map<String,Object> expMap = new HashMap<String, Object>();
		expMap.put("userID", session.getAttribute("userID").toString());
		expMap.put("unitCode", session.getAttribute("unitCode").toString());
		Map<String,Object> valueMap = new HashMap<String, Object>();
		valueMap.put("userName", userName);
		valueMap.put("sex", sex);
		valueMap.put("birthday", birthday);
		valueMap.put("IDcard",IDcard );
		valueMap.put("phone", phone);
		valueMap.put("userAge", userAge);
		valueMap.put("hireDate",hireDate );
		Map<String,Object> map = updateser.updateUser(expMap,valueMap);
		return map;
	}
	//修改用户权限
	@RequestMapping("/setuserAut")
	@ResponseBody
	public Map<String,Object> setuserAut(
			@RequestParam("userAut") String userAut,
			@RequestParam("workcontent") String workcontent,
			HttpSession session			){
		Map<String,Object> expMap = new HashMap<String, Object>();
		expMap.put("userID", session.getAttribute("userID").toString());
		expMap.put("unitCode", session.getAttribute("unitCode").toString());
		Map<String,Object> valueMap = new HashMap<String, Object>();
		valueMap.put("userAut", userAut);
		valueMap.put("workcontent", workcontent);
		Map<String,Object> map = updateser.updateUser(expMap,valueMap);
		return map;
	}
	//修改用户权限
	@RequestMapping("/approve")
	@ResponseBody
	public Map<String,Object> approve(
			@RequestParam("applyNumber") String applyNumber,
			@RequestParam("result") String state,
			@RequestParam("remark") String context,
			HttpSession session			){
		Map<String,Object> expMap = new HashMap<String, Object>();
		expMap.put("applyNumber", applyNumber);
		expMap.put("unitCode", session.getAttribute("unitCode").toString());
		Map<String,Object> valueMap = new HashMap<String, Object>();
		valueMap.put("state", state);
		valueMap.put("context", context);
		valueMap.put("approvedate", Dictionary.df_DAY.format(new Date()));
		valueMap.put("approveID", session.getAttribute("userID").toString());
		Map<String,Object> map = updateser.updateApprove(expMap,valueMap);
		return map;
	}
}
