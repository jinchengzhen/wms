package com.wms.controller.funcControl.infoMag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.wms.service.ExportService;

import common.constant.Constant;

@RequestMapping(value = "/export",method = RequestMethod.POST,
consumes = "application/json",produces = "application/json")
@Controller
public class ExportFileController {
	private static Logger logger = Logger.getLogger(ExportFileController.class);
	@Autowired
	private ExportService exportser;
	//导出人员excel
	@RequestMapping("/userXls")
	@ResponseBody
	public Map<String,Object> exportuserXls(@RequestParam("userName") String userName,
			@RequestParam("IDcard") String IDcard,HttpServletRequest request	){
		String filename = "user"+System.currentTimeMillis()+Constant.EXCEL_SUFFIX;
		String path = request.getSession().getServletContext().getRealPath(filename);
		File file=new File(path);
		String webPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+filename;
		Map<String,Object> map = exportser.exportuserXls(userName,IDcard,request.getSession().getAttribute("unitCode").toString(),file,webPath);
		return map;
	}
	//导出货品excel
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
	//上传文件
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String,Object> uploadFile(
			HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		StringBuilder sb = new StringBuilder();
		String path = request.getSession().getServletContext().getRealPath("")+"\\upload\\record\\";
	   //获取解析器  
	     CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	     //判断是否是文件  
	     if(resolver.isMultipart(request)){  
	         //进行转换  
	         MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);  
	         //获取所有文件名称  
	         Iterator<String> it = multiRequest.getFileNames();  
	         while(it.hasNext()){  
	             //根据文件名称取文件  
	             MultipartFile file = multiRequest.getFile(it.next());  
	             String fileName = file.getOriginalFilename();  
	             String localPath = path + fileName; 
	             String webpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/record/"+fileName;
	             //创建一个新的文件对象，创建时需要一个参数，参数是文件所需要保存的位置
	             File newFile = new File(localPath);  
	             if (newFile.getParentFile() != null || !newFile.getParentFile().isDirectory()) {
	                // 创建父文件夹
	                newFile.getParentFile().mkdirs();
	             }
	             //上传的文件写入到指定的文件中   
	             try {
					file.transferTo(newFile);
					sb.append(fileName+"上传成功！");
					result.put("furl", webpath);
				} catch (IllegalStateException | IOException e) {
					logger.warn(e.getMessage());
					sb.append(fileName+"上传失败！");
					result.put("furl", "");
				}
	         }
	     }
	     result.put("message", sb.toString());
		return result;
	}
}
