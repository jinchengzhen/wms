package com.wms.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionManage {
	private static Map<String, HttpSession> UserMap = new HashMap<String, HttpSession>();
	public static void putSession(String key,HttpSession session) {
		UserMap.put(key, session);
	}
	public static void removeSession(String key) {
		UserMap.remove(key);
	}
	public static HttpSession getSession(String key) {
		return UserMap.get(key);
	}
	public static int getUserNum() {
		return UserMap.size();
	}
}
