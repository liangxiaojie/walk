package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.app.common.Notes;
import com.app.service.AppCountService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.service.BaseService;

public class AppCountServiceImpl extends BaseService implements AppCountService
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

	@Override
	public Map<String, String> getAppCount(HttpServletRequest request)
	{
		Map rtnMap = new HashMap();
		
		Map<String, String> paraMap = new HashMap<String, String>();
		try 
		{			
			paraMap.put("statu", Notes.AppPathStatuActive); // 0未删除 1已删除
			String pathCount = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppPathCounts", paraMap);
			String pathCountTodayRate = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppPathCountsTodayRate", paraMap);
			rtnMap.put("pathCount", pathCount);
			rtnMap.put("pathCountTodayRate", pathCountTodayRate);
	
			paraMap = new HashMap<String, String>();
			paraMap.put("statu", Notes.AppEvaluationStatuActive); // 0未删除 1已删除
			String evalCount = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppEvalCounts", paraMap);
			String evalCountTodayRate = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppEvalCountsTodayRate", paraMap);
			rtnMap.put("evalCount", evalCount);
			rtnMap.put("evalCountTodayRate", String.valueOf(Math.rint(Integer.parseInt(evalCountTodayRate))));
	
			paraMap = new HashMap<String, String>();
			paraMap.put("statu", Notes.AppInfoStatuActive); // 0未删除 1已删除
			String infoCount = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppInfoCounts", paraMap);
			String infoCountTodayRate = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppInfoCountsTodayRate", paraMap);
			rtnMap.put("infoCount", infoCount);
			rtnMap.put("infoCountTodayRate", String.valueOf(Math.rint(Integer.parseInt(infoCountTodayRate))));
	
	
			paraMap = new HashMap<String, String>();
			paraMap.put("statu", Notes.AppUserFlagActive); // 0未删除 1已删除
			String userCount = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppUserCounts", paraMap);
			rtnMap.put("userCount", userCount);
	
			
			String allCountsToday = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppAllCountsToday");
			rtnMap.put("allCountsToday", allCountsToday);
			String allCounts = (String)getDaoSupportTemplate().get("AppCount.Mapper.getAppAllCounts");
			rtnMap.put("allCounts", allCounts);
	
			int allCountsTodayRate =  (int)Math.round(Double.parseDouble(allCountsToday)/Double.parseDouble(allCounts) * 100); 
			rtnMap.put("allCountsTodayRate", String.valueOf(allCountsTodayRate));
	
			List appAllRecord = (List)getDaoSupportTemplate().query4list("AppCount.Mapper.getAppAllRecord");
			rtnMap.put("appAllRecord", appAllRecord);
			
			
	
			List appAllEval = (List)getDaoSupportTemplate().query4list("AppCount.Mapper.getAppEvalType");
			HashMap evalMap = new HashMap();
			for(int i = 0; i < appAllEval.size(); i++)
			{
				if (appAllEval.get(i) != null)
				{
					String[] types = ((String)appAllEval.get(i)).split(",");
					for (int j = 0; j < types.length; j++)
					{
						if (!evalMap.containsKey("b"+types[j]))
						{
							HashMap tmpMap = new HashMap();
							if (types[j].equals("1"))
							{
								tmpMap.put("label", "人行道宽度窄");
								tmpMap.put("color", "#ff7c81");
							}else if (types[j].equals("2"))
							{
								tmpMap.put("label", "人行道无盲道");
								tmpMap.put("color", "#92d14f");
							}else if (types[j].equals("3"))
							{
								tmpMap.put("label", "盲道有障碍物");
								tmpMap.put("color", "#01b0f1");
							}else if (types[j].equals("4"))
							{
								tmpMap.put("label", "人行道不平整");
								tmpMap.put("color", "#f59c58");
							}else if (types[j].equals("5"))
							{
								tmpMap.put("label", "绿化环境差");
								tmpMap.put("color", "#ff9800");
							}else if (types[j].equals("6"))
							{
								tmpMap.put("label", "人行道与非机动车道未隔离");
								tmpMap.put("color", "#0071c1");
							}else if (types[j].equals("7"))
							{
								tmpMap.put("label", "人行道有障碍物");
								tmpMap.put("color", "#7030a0");
							}else if (types[j].equals("8"))
							{
								tmpMap.put("label", "缺少过街设施");
								tmpMap.put("color", "#00af50");
							}else if (types[j].equals("9"))
							{
								tmpMap.put("label", "过街距离过长");
								tmpMap.put("color", "#33cc33");
							}else if (types[j].equals("10"))
							{
								tmpMap.put("label", "过街信号灯过短");
								tmpMap.put("color", "#ff99cb");
							}else if (types[j].equals("11"))
							{
								tmpMap.put("label", "信号灯等待时间过长");
								tmpMap.put("color", "#ff7a7f");
							}else if (types[j].equals("12"))
							{
								tmpMap.put("label", "缺少人行道");
								tmpMap.put("color", "#00ccff");
							}
	
							tmpMap.put("data", 1);
							evalMap.put("b"+types[j], tmpMap);
						}else
						{
							HashMap tmpMap = (HashMap)evalMap.get("b"+types[j]);
							tmpMap.put("data", Integer.parseInt(String.valueOf(tmpMap.get("data")))+1);
							evalMap.put("b"+types[j], tmpMap);
						}
					}
				}
			}
			List evalList = new ArrayList();
			Set evalSet = evalMap.keySet();
			for (Iterator iter = evalSet.iterator(); iter.hasNext();) 
			{
				String str = (String)iter.next();
				evalList.add(evalMap.get(str));
			}
			rtnMap.put("evalMap", JSONArray.fromObject(evalList));		
	
			List appAllInfo = (List)getDaoSupportTemplate().query4list("AppCount.Mapper.getAppInfoType");
			HashMap infoMap = new HashMap();
			for(int i = 0; i < appAllInfo.size(); i++)
			{
				if (appAllInfo.get(i) != null)
				{
					String[] types = ((String)appAllInfo.get(i)).split(",");
					for (int j = 0; j < types.length; j++)
					{
						if (!infoMap.containsKey("a"+types[j]))
						{
							HashMap tmpMap = new HashMap();
							if (types[j].equals("1"))
							{
								tmpMap.put("label", "人行跨路桥");
								tmpMap.put("color", "#ff7c81");
							}else if (types[j].equals("2"))
							{
								tmpMap.put("label", "栅栏");
								tmpMap.put("color", "#92d14f");
							}else if (types[j].equals("3"))
							{
								tmpMap.put("label", "照明设施");
								tmpMap.put("color", "#01b0f1");
							}else if (types[j].equals("4"))
							{
								tmpMap.put("label", "视线诱导标志");
								tmpMap.put("color", "#f59c58");
							}else if (types[j].equals("5"))
							{
								tmpMap.put("label", "公路反射镜");
								tmpMap.put("color", "#ff9800");
							}else if (types[j].equals("6"))
							{
								tmpMap.put("label", "公路情报板");
								tmpMap.put("color", "#0071c1");
							}else if (types[j].equals("7"))
							{
								tmpMap.put("label", "公路监视系统");
								tmpMap.put("color", "#7030a0");
							}else if (types[j].equals("8"))
							{
								tmpMap.put("label", "停车场");
								tmpMap.put("color", "#00af50");
							}else if (types[j].equals("9"))
							{
								tmpMap.put("label", "公共汽车停靠站");
								tmpMap.put("color", "#33cc33");
							}
	
							tmpMap.put("data", 1);
							infoMap.put("a"+types[j], tmpMap);
						}else
						{
							HashMap tmpMap = (HashMap)infoMap.get("a"+types[j]);
							tmpMap.put("data", Integer.parseInt(String.valueOf(tmpMap.get("data")))+1);
							infoMap.put("a"+types[j], tmpMap);
						}
					}
				}
			}
	
			List infoList = new ArrayList();
			Set infoSet = infoMap.keySet();
			for (Iterator iter = infoSet.iterator(); iter.hasNext();) 
			{
				String str = (String)iter.next();
				infoList.add(infoMap.get(str));
			}
			rtnMap.put("infoMap", JSONArray.fromObject(infoList));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return rtnMap;
	}
}