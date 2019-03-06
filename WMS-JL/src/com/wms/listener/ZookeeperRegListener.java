package com.wms.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.wms.dictloader.Dictionary;

import common.jdbc.CommonDao;
import common.util.PropertyUtil;

/**
* @ClassName: ZookeeperRegListener
* @Description: ServletContext 对象是一个为整个 web 应用提供共享的内存，任何请求都可以访问里面的内容
* 	在 Servlet API 中有一个 ServletContextListener 接口，它能够监听 ServletContext 对象的生命周期，实际上就是监听 Web 应用的生命周期。
　　	当Servlet 容器启动或终止Web 应用时，会触发ServletContextEvent 事件，该事件由ServletContextListener 来处理。
	在 ServletContextListener 接口中定义了处理ServletContextEvent 事件的两个方法。
* @author jcz
* @date 2019年1月14日
 */
public class ZookeeperRegListener implements ServletContextListener{
	private static Logger logger = Logger.getLogger(ZookeeperRegListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String realPath = event.getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"classes"+File.separator+"log4j.properties";
		PropertyConfigurator.configure(realPath);
		//读取自定义配置文件目录集合
		String configRealPath = event.getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"classes"+File.separator+"config"+File.separator+"jdbc.properties";
		//增加缓存map作为配置临时存储位置
		PropertyUtil.init(configRealPath);
		//连接数据库
		CommonDao.SQLDBInit(PropertyUtil.getDBconnMap());
		logger.info("初始化加载字典数据信息......");
		Dictionary.init();
	}

}
