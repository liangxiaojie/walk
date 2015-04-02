package com.app.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.service.AppCountService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.service.BaseService;

public class AppCountServiceImpl extends BaseService implements AppCountService {
	private DaoSupportTemplate daoSupportTemplate;

	public DaoSupportTemplate getDaoSupportTemplate() {
		return daoSupportTemplate;
	}

	public void setDaoSupportTemplate(DaoSupportTemplate daoSupportTemplate) {
		this.daoSupportTemplate = daoSupportTemplate;
	}

	@Override
	public Map<String, String> getAppCountToday(HttpServletRequest request) {
		Map<String, String> rtnMap = (Map<String, String>) getDaoSupportTemplate().get("AppEvaluation.Mapper.getAppEvaluationsCount");
		return rtnMap;
	}

	@Override
	public Map<String, String> getAppCountLatestUpdate(HttpServletRequest request) {
		return null;
	}

}
