package com.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kk.framework.dao.Page;

public interface AppPathService
{
	public Page searchAppPath(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> queryAppPath(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> saveAppPath(Map<String, String> parasMap, HttpServletRequest request);
//	public HashMap<String, String> queryAppPath(Map<String, String> paraMap, HttpServletRequest request);

//	public HashMap<String, String> saveAppPath(Map<String, String> paraMap, HttpServletRequest request);
	
	public Page getAppPaths(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> getAppPathsCount(HttpServletRequest request);
}