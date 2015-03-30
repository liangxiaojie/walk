package com.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kk.framework.dao.Page;

public interface AppEvaluationService
{
	public Page searchAppEvaluation(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> queryAppEvaluation(Map<String, String> parasMap, HttpServletRequest request);

	public Map<String, String> saveAppEvaluation(Map<String, String> parasMap, HttpServletRequest request);
	
//	public HashMap<String, String> queryAppEvaluation(Map<String, String> paraMap, HttpServletRequest request);

//	public HashMap<String, String> saveAppEvaluation(Map<String, String> paraMap, HttpServletRequest request);
	
	public Page getAppEvaluations(Map<String, String> parasMap, HttpServletRequest request);
}