package com.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AppCountService
{
	public Map<String, String> getAppCount(HttpServletRequest request);
}
