package com.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kk.framework.dao.Page;

public interface AppInfoService
{
	public Page searchAppInfo(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> queryAppInfo(Map<String, String> parasMap, HttpServletRequest request);
	
	public Map<String, String> saveAppInfo(Map<String, String> parasMap, HttpServletRequest request);
//	public HashMap<String, String> queryAppInfo(Map<String, String> paraMap, HttpServletRequest request);

//	public HashMap<String, String> saveAppInfo(Map<String, String> paraMap, HttpServletRequest request);
	
	public Page getAppInfos(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> getAppInfosCount(HttpServletRequest request);
}