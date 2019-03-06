package com.wms.controller.funcControl.infoMag;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wms.service.ExportService;

@RequestMapping(value = "/export",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class ExportFileController {
	@Autowired
	private ExportService exportser;
	//导出人员excel
	@RequestMapping("/userXls")
	@ResponseBody
	public Map<String,Object> exportuserXls(@RequestParam("userName") String userName,
			@RequestParam("IDcard") String IDcard,HttpServletRequest request	){
		String filename = "user"+System.currentTimeMillis()+".xls";
		String path = request.getSession().getServletContext().getRealPath(filename);
		File file=new File(path);
		String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+filename;
		Map<String,Object> map = exportser.exportuserXls(userName,IDcard,request.getSession().getAttribute("unitCode").toString(),file,webPath);
		return map;
	}
	//导出人员excel
	@RequestMapping("/goodsXls")
	@ResponseBody
	public Map<String,Object> exportgoodsXls(@RequestParam("goodsID") String goodsID,
			@RequestParam("typeID") String typeID,
			@RequestParam("goodsSource") String goodsSource,
			HttpServletRequest request	){
		String filename = "goods"+System.currentTimeMillis()+".xls";
		String path = request.getSession().getServletContext().getRealPath(filename);
		File file=new File(path);
		String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+filename;
		Map<String,Object> map = exportser.exportgoodsXls(goodsID,typeID,goodsSource,request.getSession().getAttribute("unitCode").toString(),file,webPath);
		return map;
	}
}
