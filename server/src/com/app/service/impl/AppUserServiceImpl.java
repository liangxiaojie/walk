package com.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.common.Notes;
import com.app.domain.AppUser;
import com.app.service.AppUserService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.service.BaseService;

public class AppUserServiceImpl extends BaseService implements AppUserService
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

	public HashMap<String, String> login(Map<String, String> parasMap, HttpServletRequest request)
	{
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", parasMap.get("userId"));
		paraMap.put("userPass", parasMap.get("userPass"));
		paraMap.put("flag", Notes.AppUserFlagActive);		//0未删除  1已删除

		List<AppUser> list = (List<AppUser>) getDaoSupportTemplate().query4list("AppUser.Mapper.checkAppUserExist", paraMap);
		if (list == null || list.size() < 1)
		{
			rtnMap.put("code",	"E001");
			rtnMap.put("msg", 	"用户不存在，请确认后重新输入！");
		}else
		{
			AppUser appUser = list.get(0);
			if (parasMap.get("userPass") == null || appUser.getUserPass() == null || !appUser.getUserPass().equals(parasMap.get("userPass")))
			{
				rtnMap.put("code",	"E002");
				rtnMap.put("msg", 	"密码不正确，请确认后重新输入！");
			}else if (appUser.getStatu().equals(Notes.AppUserStatuDisable))	//0：启用  1：禁用
			{
				rtnMap.put("code",	"E003");
				rtnMap.put("msg", 	"您的账号已被禁用！");
			}else
			{
				rtnMap.put("code",	"S001");
				rtnMap.put("msg", 	"登录成功！");
				
				paraMap = new HashMap<String, String>();
				paraMap.put("id", appUser.getId());
				daoSupportTemplate.update("AppUser.Mapper.updateAppUserLogTime", paraMap);
				request.getSession().setAttribute(Notes.APPUSER_LOGIN_SESSION, appUser);
			}
		}

		return rtnMap;
	}

	public HashMap<String, String> getAppUserInfo(HttpServletRequest request)
	{
		HashMap<String, String> rtnMap = null;
		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);

		if (appUser != null)
		{
			rtnMap = new HashMap<String, String>();
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("id", appUser.getId());

			AppUser appUser1 = (AppUser) getDaoSupportTemplate().get("AppUser.Mapper.getAppUserInfo", paraMap);

			rtnMap.put("id", appUser1.getId());
			rtnMap.put("userId", appUser1.getUserId());
			rtnMap.put("userName", appUser1.getUserName());
			rtnMap.put("phoneNumber", appUser1.getPhoneNumber());
			rtnMap.put("createTime", String.valueOf(appUser1.getCreateTime()));
			rtnMap.put("lastLogtime", String.valueOf(appUser1.getLastLogtime()));	
		}

		return rtnMap;
	}

}