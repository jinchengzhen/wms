package com.wms.interceptors;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SystemSessionInterceptor implements HandlerInterceptor {
//	private static Logger logger = Logger.getLogger(SystemSessionInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
			HttpSession session=request.getSession(true); 
//			String login_flag =  request.getParameter("login_flag");
//			if("login_flag".equals(login_flag)) {
//				return true;
//			}else {
				String type = request.getHeader("X-Requested-With");// XMLHttpRequest
				//session中获取用户名信息 
				if(session.getAttribute("loginTime") == null) {
					// 重定向
					String path = request.getContextPath();
					String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
					response.setContentType("text/html");  
					response.setCharacterEncoding("utf-8");  
					// 转发
					if ("XMLHttpRequest".equals(type)) {
						// ajax请求
						response.setHeader("SESSIONSTATUS", "TIMEOUT");
						response.setHeader("CONTEXTPATH", basePath);
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						return false;
						
					} else {
						response.sendRedirect(basePath);
						return false;
					}
				}
//			}
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
