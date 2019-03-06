package com.wms.interceptors;



import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class DeleteInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
			HttpSession session=request.getSession(true); 
			String aut = session.getAttribute("duty").toString();
			int autnum = Integer.parseInt(aut);
			if(autnum > 1) {
				response.setContentType("text/html");  
				response.setCharacterEncoding("utf-8");  
				PrintWriter out = response.getWriter();    
				StringBuilder builder = new StringBuilder();    
				builder.append("{\"message\":\"您没有此操作权限！\"}");    
				out.print(builder.toString());    
				out.close();    	
				return false;
			}
			return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}


}
