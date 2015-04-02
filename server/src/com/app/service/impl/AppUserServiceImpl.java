package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.common.Notes;
import com.app.domain.AppUser;
import com.app.service.AppUserService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.dao.Page;
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

	@Override
	public HashMap<String, String> saveAppUserInfo(Map<String, String> parasMap,HttpServletRequest request) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userid", parasMap.get("userid"));
		paraMap.put("nickname", parasMap.get("nickname"));
		paraMap.put("pass", parasMap.get("pass"));
		paraMap.put("passQueren",  parasMap.get("passQueren"));
		paraMap.put("phone", parasMap.get("phone"));
		paraMap.put("statu", Notes.AppUserStatuEnable);
		paraMap.put("flag", Notes.AppUserFlagActive);
		
		if(paraMap.get("userid")==null || paraMap.get("userid").equals("")){
			rtnMap.put("code",	"E001");
			rtnMap.put("msg", 	"用户名不能为空！");
		}else if(paraMap.get("nickname")==null || paraMap.get("nickname").equals("")){
			rtnMap.put("code",	"E002");
			rtnMap.put("msg", 	"昵称不能为空！");
		} else{
			List<AppUser> list = (List<AppUser>) getDaoSupportTemplate().query4list("AppUser.Mapper.checkAppUserExist1", paraMap);
			if (list == null || list.size() < 1)
			{
				if (paraMap.get("pass") == null || paraMap.get("pass").equals("") || paraMap.get("passQueren") == null || paraMap.get("passQueren").equals("") || !paraMap.get("pass").equals(paraMap.get("passQueren")))
				{
					rtnMap.put("code",	"E002");
					rtnMap.put("msg", 	"密码为空或两次输入密码不一致，请确认后重新输入！");
				}else{
					paraMap.put("id", java.util.UUID.randomUUID().toString().replaceAll("-", ""));
					daoSupportTemplate.insert("AppUser.Mapper.saveAppUser", paraMap);
					rtnMap.put("code", "S001");
					rtnMap.put("msg", "注册成功！");
				}
			}else
			{
				rtnMap.put("code",	"E001");
				rtnMap.put("msg", 	"该用户名已被注册，请确认后重新输入！");
			}
		}
		return rtnMap;
	}

	@Override
	public HashMap<String, String> updateAppUserPass(Map<String, String> parasMap, HttpServletRequest request) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		
		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		
		paraMap.put("passOld", parasMap.get("passOld"));
		paraMap.put("pass", parasMap.get("pass"));
		paraMap.put("passQueren", parasMap.get("passQueren"));
		
		
		if(appUser==null){
			rtnMap.put("code",	"E001");
			rtnMap.put("msg", 	"无法获取登录信息，请重新登录");
		} else if(!appUser.getUserPass().equals(paraMap.get("passOld"))){
			rtnMap.put("code",	"E002");
			rtnMap.put("msg", 	"原密码输入错误，请重新输入");
		} else if(paraMap.get("pass") == null || paraMap.get("pass").equals("") || paraMap.get("passQueren") == null || paraMap.get("passQueren").equals("") || !paraMap.get("pass").equals(paraMap.get("passQueren"))){
			rtnMap.put("code",	"E003");
			rtnMap.put("msg", 	"密码为空或两次输入密码不一致，请确认后重新输入！");
		} else {
			paraMap.put("id", appUser.getId());
			daoSupportTemplate.update("AppUser.Mapper.updateAppUserPass", paraMap);
			rtnMap.put("code", "S001");
			rtnMap.put("msg", "恭喜你，密码修改成功！");
		}
		return rtnMap;
	}

	@Override
	public Page getAppUserUsers(Map<String, String> parasMap, HttpServletRequest request) {
		List rtnList = new ArrayList ();
		HashMap<String, String> rtnMap = new HashMap<String, String>();

//		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		Map<String, String> paraMap = new HashMap<String, String>();
//		paraMap.put("userId", appUser.getUserId());
//		paraMap.put("statu", Notes.AppPathStatuActive);

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("AppUser.Mapper.getAppUserUsers", "AppUser.Mapper.getAppUserUsersCount", paraMap, pageNo, pageSize);

		return page;
	}

	@Override
	public Map<String, String> getAppUsersCount(HttpServletRequest request) {
		Map<String, String> rtnMap = (Map<String, String>) getDaoSupportTemplate().get("AppUser.Mapper.getAppUsersCount");
		return rtnMap;
	}

}