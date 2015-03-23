package com.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.service.AppPictureService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.service.BaseService;

public class AppPictureServiceImpl extends BaseService implements AppPictureService
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
	
	public List<HashMap<String, String>> queryAppPicture(Map<String, String> parasMap)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("id", parasMap.get("id"));
		paraMap.put("walktype", parasMap.get("walktype"));

		List<HashMap<String, String>> rtnList = (List<HashMap<String, String>>)getDaoSupportTemplate().query4list("AppPicture.Mapper.queryAppPicture", paraMap);

		return rtnList;
	}

}