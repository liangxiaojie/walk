package com.app.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kk.framework.dao.Page;

public interface ServerUserService {
	public HashMap<String, String> login(Map<String, String> paraMap, HttpServletRequest request);

	public HashMap<String, String> getServerUserInfo(HttpServletRequest request);
	
	public HashMap<String, String> saveServerUserInfo(Map<String, String> parasMap,HttpServletRequest request);

	public Page getServerUserUsers(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> deleteSysUser(Map<String, String> parasMap, HttpServletRequest request);
}
