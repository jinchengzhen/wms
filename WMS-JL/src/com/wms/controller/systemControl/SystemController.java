package com.wms.controller.systemControl;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.dictloader.Dictionary;
import com.wms.dictloader.Dictloader_NameID;
import com.wms.dictloader.Dictloader_SelectData;
import com.wms.manage.MenuManage;
import com.wms.manage.SessionManage;
import com.wms.service.SystemService;

import common.util.RSAUtils;


@RequestMapping(value = "/system",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class SystemController {
	private static Logger logger = Logger.getLogger(SystemController.class);
	@Autowired
	private SystemService ser;
	//登录验证
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,String> login(@RequestParam("userID") String userID,
						@RequestParam("password") String password,
						HttpSession session,HttpServletRequest request,HttpServletResponse response){
		HttpSession sessionOld = SessionManage.getSession(userID);
		if(sessionOld != null) {
			if(session.getId().equals(sessionOld.getId())) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("state", "0");
				map.put("message", "此用户已登录请勿重复登录！");
				return map;
			}
		}
		//密码解密
		RSAPrivateKey privateKey = (RSAPrivateKey)request.getSession().getAttribute("privateKey");  
        if(privateKey!=null){  
            long time1 = System.currentTimeMillis();  
            try {
				password = RSAUtils.decryptByPrivateKey(password, privateKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            logger.info("decrypt cost time:"+(Double)((System.currentTimeMillis()-time1)/1000d) +"s");  
        }  
		Map<String,String> map = ser.select(userID, password);
		if(map.get("state").equals("1")) {
            request.getSession().removeAttribute("privateKey");  
			if(sessionOld != null ) {
				String validate = sessionOld.getAttribute("userID") == null ? "":sessionOld.getAttribute("userID").toString();
				if(validate != null || !"".equals(validate)) {
					sessionOld.invalidate();
				}
			}
			//用户信息
			for(Entry<String, String> entry : map.entrySet()) {
				session.setAttribute(entry.getKey(), entry.getValue());
			}
			session.setAttribute("loginTime", Dictionary.df_DTIME.format(new Date()));
			SessionManage.putSession(userID, session);
			logger.info("访问人："+session.getAttribute("userName")+"，id："+session.getAttribute("userID")+" 成功登录系统. 当前在线人数："+SessionManage.getUserNum());
		}
		return map;
	}
	//获取注册信息下拉框unitProperties、unitType
	@RequestMapping("/selectInfo")
	@ResponseBody
	public Map<String,List<Map<String,Object>>> getRegistSelect(){
		return Dictloader_SelectData.getMultiSelectData("unitProperties","unitType");
	}
	//注册提交
	@RequestMapping("/registDeal")
	@ResponseBody
	public Map<String,String> registDeal(
			@RequestParam("register") String register,
			@RequestParam("registerIDcard") String registerIDcard,
			@RequestParam("registerPhone") String registerPhone,
			@RequestParam("registEmail") String registEmail,
			@RequestParam("password") String password,
			@RequestParam("unitProperties") String unitProperties,
			@RequestParam("unitType") String unitType,
			@RequestParam("unitName") String unitName,
			@RequestParam("location") String location,
			@RequestParam("address") String address,	
			@RequestParam("flaginfo") String flaginfo	
			) {
		//md5加签验证	
		Map<String,String> map = Dictionary.checkMD5(password+unitType+registerIDcard,flaginfo);
		if(map == null) {
			return ser.registDeal(register,registerIDcard,registerPhone,registEmail,password,unitProperties,unitType,unitName,location,address);
		}else {
			return map;
		}
	}
	//初始化菜单
	@RequestMapping("/initMenulist")
	@ResponseBody
	public Map<String,Object> initMenulist(HttpSession session) {
		return MenuManage.getMenuObj(session.getAttribute("workcontent").toString().split("-"));
	}
	//获取菜单所有选项
	@RequestMapping("/getworkcontent")
	@ResponseBody
	public Map<String,Object> getworkcontent() {
		return MenuManage.getMenuObj(MenuManage.getMenuStr().split("-"));
	}
	//验证session
	@RequestMapping("/checkSession")
	@ResponseBody
	public void checkSession(HttpSession session) {
	}
	//验证原始密码
	@RequestMapping("/checkword")
	@ResponseBody
	public Map<String,String> checkword(@RequestParam("password") String password,HttpSession session) {
		Map<String,String> map = new HashMap<String,String>();
		RSAPrivateKey privateKey = (RSAPrivateKey)session.getAttribute("privateKey");  
        if(privateKey!=null){  
            long time1 = System.currentTimeMillis();  
            try {
				password = RSAUtils.decryptByPrivateKey(password, privateKey);
				if(password.equals(session.getAttribute("password").toString())) {
					map.put("result", "1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  
            logger.info("修改密码（原始密码）验证时间:"+(Double)((System.currentTimeMillis()-time1)/1000d) +"s");  
        }  
		return map;
	}
	//修改密码
	@RequestMapping("/updatekey")
	@ResponseBody
	public Map<String,String> updatekey(@RequestParam("password") String password,HttpSession session) {
		Map<String,String> map = new HashMap<String,String>();
		RSAPrivateKey privateKey = (RSAPrivateKey)session.getAttribute("privateKey");  
        if(privateKey!=null){  
            long time1 = System.currentTimeMillis();  
            try {
				password = RSAUtils.decryptByPrivateKey(password, privateKey);
				map = ser.updatekey(password,session.getAttribute("userID").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}  
            logger.info("修改密码（新密码）验证时间:"+(Double)((System.currentTimeMillis()-time1)/1000d) +"s");  
        }  
		return map;
	}
	//获取个人信息
	@RequestMapping("/getpersonInfo")
	@ResponseBody
	public Map<String,String> getpersonInfo(HttpSession session) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", session.getAttribute("userName").toString());
		String sex = "1".equals(session.getAttribute("sex").toString()) ? "男":"女";
		map.put("sex", sex);
		map.put("userAge", session.getAttribute("userAge").toString());
		map.put("birthday", session.getAttribute("birthday").toString());
		map.put("IDcard", session.getAttribute("IDcard").toString());
		map.put("phone", session.getAttribute("phone").toString());
		map.put("departCode", Dictloader_NameID.getUnitName(session.getAttribute("unitCode").toString()));
		map.put("hireDate", session.getAttribute("hireDate").toString());
		return map;
	}
	//退出系统
	@RequestMapping("/quit")
	@ResponseBody
	public Map<String,String> quit(HttpSession session) {
		Map<String,String> map = new HashMap<String,String>();
		String userID = session.getAttribute("userID").toString();
		HttpSession sessionOld = SessionManage.getSession(userID);
		sessionOld.invalidate();
		SessionManage.removeSession(userID);
		map.put("url", "login.jsp");
		return map;
	}
			
}
