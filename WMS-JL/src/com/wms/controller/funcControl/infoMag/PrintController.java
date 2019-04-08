package com.wms.controller.funcControl.infoMag;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.constant.Constant;
import common.util.JbarCodeUtil;

@RequestMapping(value = "/printor",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class PrintController {
	@RequestMapping("/generateCode")
	@ResponseBody
	public Map<String,Object> generateCode(@RequestParam("SKU") String SKU,
			@RequestParam("goodsName") String goodsName,
			HttpSession session, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		String filename = Constant.TEMP_FILE+File.separator+SKU+Constant.PNG_SUFFIX;
		String path = session.getServletContext().getRealPath(filename);
		File file = new File(path);
		if(JbarCodeUtil.createBarcode(SKU, file, goodsName)) {
			String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+filename;
			result.put("url", webPath);
		}
		return result;
	}
}
