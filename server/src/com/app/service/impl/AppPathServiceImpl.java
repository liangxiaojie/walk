package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.common.Notes;
import com.app.domain.AppUser;
import com.app.service.AppPathService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.dao.Page;
import com.kk.framework.service.BaseService;

public class AppPathServiceImpl extends BaseService implements AppPathService
{
	private DaoSupportTemplate daoSupportTemplate;

	public DaoSupportTemplate getDaoSupportTemplate()
	{
		return daoSupportTemplate;
	}

	public void setDaoSupportTemplate(DaoSupportTemplate daoSupportTemplate)
	{
		this.daoSupportTemplate = daoSupportTemplate;
	}

	public Page searchAppPath(Map<String, String> parasMap, HttpServletRequest request)
	{
		List rtnList = new ArrayList ();
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", appUser.getUserId());
		paraMap.put("statu", Notes.AppPathStatuActive);

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("AppPath.Mapper.searchAppPath", "AppPath.Mapper.searchAppPathCount", paraMap, pageNo, pageSize);

		return page;
	}

	public Map<String, String> queryAppPath(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("id", parasMap.get("id"));

		Map<String, String> rtnMap = (Map<String, String>)getDaoSupportTemplate().get("AppPath.Mapper.queryAppPath", paraMap);

		return rtnMap;
	}
	public Map<String, String> saveAppPath(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> paraMap = new HashMap<String, String>();

		String id = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		paraMap.put("id", id);
		
		paraMap.put("sadd",parasMap.get("sadd"));
		paraMap.put("sdate",parasMap.get("sdate"));
		paraMap.put("slon",parasMap.get("slon"));
		paraMap.put("slat",parasMap.get("slat"));
		paraMap.put("eadd",parasMap.get("eadd"));
		paraMap.put("edate",parasMap.get("edate"));
		paraMap.put("elon",parasMap.get("elon"));
		paraMap.put("elat",parasMap.get("elat"));
		paraMap.put("distance",parasMap.get("distance"));
		paraMap.put("costtime",parasMap.get("costtime"));
		paraMap.put("proce",parasMap.get("proce"));
		paraMap.put("kpi1",parasMap.get("kpi1"));
		paraMap.put("kpi2",parasMap.get("kpi2"));
		paraMap.put("kpi3",parasMap.get("kpi3"));
		paraMap.put("kpi",parasMap.get("kpi"));
		paraMap.put("suggest",parasMap.get("suggest"));

		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		paraMap.put("user_id", appUser.getUserId());
		paraMap.put("statu", Notes.AppPathStatuActive);

		Map<String, String> rtnMap = new HashMap<String, String>();
		daoSupportTemplate.update("AppPath.Mapper.saveAppPath", paraMap);

		rtnMap.put("code", "S001");
		rtnMap.put("msg", "提交成功！");
		return rtnMap;
	}

	@Override
	public Page getAppPaths(Map<String, String> parasMap,HttpServletRequest request) {

		Map<String, String> paraMap = new HashMap<String, String>();

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("AppPath.Mapper.getAppPaths", "AppPath.Mapper.getAppPathsCount", paraMap, pageNo, pageSize);

		return page;
	}

	@Override
	public Map<String, String> getAppPathsCount(HttpServletRequest request) {
		Map<String, String> rtnMap = (Map<String, String>) getDaoSupportTemplate().get("AppPath.Mapper.getAppPathsCount");
		return rtnMap;
	}

	public Map<String, String> deleteAppPath(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("id", parasMap.get("id"));
		paraMap.put("statu", Notes.AppPathStatuDeleted);

		daoSupportTemplate.delete("AppPath.Mapper.deleteAppPath", paraMap);

		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("code", "S001");
		rtnMap.put("msg", "删除成功！");
		return rtnMap;
	}
}