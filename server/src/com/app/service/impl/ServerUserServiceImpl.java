package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.common.Notes;
import com.app.domain.ServerUser;
import com.app.service.ServerUserService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.dao.Page;
import com.kk.framework.service.BaseService;

public class ServerUserServiceImpl extends BaseService implements ServerUserService {

	private DaoSupportTemplate daoSupportTemplate;

	public DaoSupportTemplate getDaoSupportTemplate() {
		return daoSupportTemplate;
	}

	public void setDaoSupportTemplate(DaoSupportTemplate daoSupportTemplate) {
		this.daoSupportTemplate = daoSupportTemplate;
	}

	@Override
	public HashMap<String, String> login(Map<String, String> parasMap, HttpServletRequest request) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", parasMap.get("userId"));
		paraMap.put("userPass", parasMap.get("userPass"));
		paraMap.put("flag", Notes.ServerUserFlagActive); // 0未删除 1已删除

		List<ServerUser> list = (List<ServerUser>) getDaoSupportTemplate().query4list("ServerUser.Mapper.checkServerUserExist", paraMap);
		if (list == null || list.size() < 1) {
			rtnMap.put("code", "E001");
			rtnMap.put("msg", "用户不存在，请确认后重新输入！");
		} else {
			ServerUser ServerUser = list.get(0);
			if (parasMap.get("userPass") == null || ServerUser.getUserPass() == null || !ServerUser.getUserPass().equals(parasMap.get("userPass"))) {
				rtnMap.put("code", "E002");
				rtnMap.put("msg", "密码不正确，请确认后重新输入！");
			} else if (ServerUser.getStatu().equals(Notes.ServerUserStatuDisable)) // 0：启用
																				// 1：禁用
			{
				rtnMap.put("code", "E003");
				rtnMap.put("msg", "您的账号已被禁用！");
			} else {
				rtnMap.put("code", "S001");
				rtnMap.put("msg", "登录成功！");

				paraMap = new HashMap<String, String>();
				paraMap.put("id", ServerUser.getId());
				daoSupportTemplate.update("ServerUser.Mapper.updateServerUserLogTime", paraMap);
				request.getSession().setAttribute(Notes.SERVERUSER_LOGIN_SESSION, ServerUser);
			}
		}

		return rtnMap;
	}

	@Override
	public HashMap<String, String> getServerUserInfo(HttpServletRequest request) {
		HashMap<String, String> rtnMap = null;
		ServerUser ServerUser = (ServerUser) request.getSession().getAttribute(Notes.SERVERUSER_LOGIN_SESSION);

		if (ServerUser != null) {
			rtnMap = new HashMap<String, String>();
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("id", ServerUser.getId());

			ServerUser ServerUser1 = (ServerUser) getDaoSupportTemplate().get("ServerUser.Mapper.getServerUserInfo", paraMap);

			rtnMap.put("id", ServerUser1.getId());
			rtnMap.put("userId", ServerUser1.getUserId());
			rtnMap.put("userName", ServerUser1.getUserName());
			rtnMap.put("phoneNumber", ServerUser1.getPhoneNumber());
			rtnMap.put("createTime", String.valueOf(ServerUser1.getCreateTime()));
			rtnMap.put("lastLogtime", String.valueOf(ServerUser1.getLastLogtime()));
		}

		return rtnMap;
	}

	@Override
	public HashMap<String, String> saveServerUserInfo(Map<String, String> parasMap, HttpServletRequest request) {
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userid", parasMap.get("userid"));
		paraMap.put("nickname", parasMap.get("nickname"));
		paraMap.put("pass", parasMap.get("pass"));
		paraMap.put("passQueren", parasMap.get("passQueren"));
		paraMap.put("phone", parasMap.get("phone"));
		paraMap.put("statu", Notes.ServerUserStatuEnable);
		paraMap.put("flag", Notes.ServerUserFlagActive);

		if (paraMap.get("userid") == null || paraMap.get("userid").equals("")) {
			rtnMap.put("code", "E001");
			rtnMap.put("msg", "用户名不能为空！");
		} else if (paraMap.get("nickname") == null || paraMap.get("nickname").equals("")) {
			rtnMap.put("code", "E002");
			rtnMap.put("msg", "昵称不能为空！");
		} else {
			List<ServerUser> list = (List<ServerUser>) getDaoSupportTemplate().query4list("ServerUser.Mapper.checkServerUserExist1", paraMap);
			if (list == null || list.size() < 1) {
				if (paraMap.get("pass") == null || paraMap.get("pass").equals("") || paraMap.get("passQueren") == null || paraMap.get("passQueren").equals("")
						|| !paraMap.get("pass").equals(paraMap.get("passQueren"))) {
					rtnMap.put("code", "E002");
					rtnMap.put("msg", "密码为空或两次输入密码不一致，请确认后重新输入！");
				} else {
					paraMap.put("id", java.util.UUID.randomUUID().toString().replaceAll("-", ""));
					daoSupportTemplate.insert("ServerUser.Mapper.saveServerUser", paraMap);
					rtnMap.put("code", "S001");
					rtnMap.put("msg", "注册成功！");
				}
			} else {
				rtnMap.put("code", "E001");
				rtnMap.put("msg", "该用户名已被注册，请确认后重新输入！");
			}
		}
		return rtnMap;
	}
	
	@Override
	public Page getServerUserUsers(Map<String, String> parasMap, HttpServletRequest request) {
		List rtnList = new ArrayList ();
		HashMap<String, String> rtnMap = new HashMap<String, String>();

//		AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		Map<String, String> paraMap = new HashMap<String, String>();
//		paraMap.put("userId", appUser.getUserId());
//		paraMap.put("statu", Notes.AppPathStatuActive);

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("ServerUser.Mapper.getServerUserUsers", "ServerUser.Mapper.getServerUsersCount", paraMap, pageNo, pageSize);

		return page;
	}
	public Map<String, String> deleteSysUser(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("id", parasMap.get("id"));
		paraMap.put("flag", Notes.ServerUserFlagDeleted);

		daoSupportTemplate.delete("ServerUser.Mapper.deleteSysUser", paraMap);

		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("code", "S001");
		rtnMap.put("msg", "删除成功！");
		return rtnMap;
	}
}
