package com.app.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kk.framework.dao.Page;

public interface AppUserService
{
	public HashMap<String, String> login(Map<String, String> paraMap, HttpServletRequest request);

	public HashMap<String, String> getAppUserInfo(HttpServletRequest request);
	
	public HashMap<String, String> saveAppUserInfo(Map<String, String> parasMap,HttpServletRequest request);
	
	public HashMap<String, String> updateAppUserPass(Map<String, String> parasMap,HttpServletRequest request);
	
	public Page getAppUserUsers(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> getAppUsersCount(HttpServletRequest request);
}