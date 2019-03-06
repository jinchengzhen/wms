package com.wms.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.wms.manage.SessionManage;



public class OnlineUserListener implements HttpSessionListener{
	private static Logger logger = Logger.getLogger(OnlineUserListener.class);
	@Override  
	public void sessionCreated(HttpSessionEvent event){
     }
	@Override
   public void sessionDestroyed(HttpSessionEvent event){
		 HttpSession session=event.getSession();
		 String userID = session.getAttribute("userID") == null ? "":session.getAttribute("userID").toString();
	     synchronized(this){
	    	 if(!"".equals(userID)) {
	    		 logger.info("访问人："+session.getAttribute("userName")+"，id："+userID+"  正在退出系统.");
	    		 SessionManage.removeSession(userID);;//从用户组中移除掉，用户组为一个map
	    	 }
	     }
     }

	
 }
