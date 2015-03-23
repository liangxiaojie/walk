package com.kk.framework.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ognl.OgnlRuntime;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kk.framework.dao.Page;
import com.kk.framework.exception.DaoException;
import com.kk.framework.exception.SystemException;
import com.kk.framework.spring.SpringBeanLoader;
import com.kk.framework.util.StringUtils;

public class JsonRpcServlet extends HttpServlet
{
	private Log logger;
	private Map methodCache;

	// private static class TimeMapper extends AbstractMapper
	// {
	//
	// public Class getHelpedClass()
	// {
	// return java / sql / Time;
	// }
	//
	// public JSONValue toJSON(Object obj) throws MapperException
	// {
	// if (obj == null)
	// return new JSONNull();
	// else
	// return new JSONString(((Time) obj).toString());
	// }
	//
	// public Object toJava(JSONValue jsonvalue, Class class1) throws
	// MapperException
	// {
	// if (!jsonvalue.isString())
	// throw new MapperException((new
	// StringBuilder()).append("TimeMapper cannot map class: ").append(jsonvalue.getClass().getName()).toString());
	// try
	// {
	// return Time.valueOf(((JSONString) jsonvalue).getValue().trim());
	// } catch (IllegalArgumentException illegalargumentexception)
	// {
	// return null;
	// }
	// }
	//
	// private TimeMapper()
	// {
	// }
	//
	// }
	//
	// private static class DateMapper extends AbstractMapper
	// {
	//
	// public Class getHelpedClass()
	// {
	// return java / sql / Date;
	// }
	//
	// public JSONValue toJSON(Object obj) throws MapperException
	// {
	// if (obj == null)
	// return new JSONNull();
	// else
	// return new JSONString(((Date) obj).toString());
	// }
	//
	// public Object toJava(JSONValue jsonvalue, Class class1) throws
	// MapperException
	// {
	// if (!jsonvalue.isString())
	// throw new MapperException((new
	// StringBuilder()).append("DateMapper cannot map class: ").append(jsonvalue.getClass().getName()).toString());
	// try
	// {
	// return Date.valueOf(((JSONString) jsonvalue).getValue().trim());
	// } catch (IllegalArgumentException illegalargumentexception)
	// {
	// return null;
	// }
	// }
	//
	// private DateMapper()
	// {
	// }
	//
	// }
	//
	// private static class TimestampMapper extends AbstractMapper
	// {
	//
	// public Class getHelpedClass()
	// {
	// return java / sql / Timestamp;
	// }
	//
	// public JSONValue toJSON(Object obj) throws MapperException
	// {
	// if (obj == null)
	// return new JSONNull();
	// else
	// return new JSONString(StringUtils.subsubstringBefore(((Timestamp)
	// obj).toString(), "."));
	// }
	//
	// public Object toJava(JSONValue jsonvalue, Class class1) throws
	// MapperException
	// {
	// if (!jsonvalue.isString())
	// throw new MapperException((new
	// StringBuilder()).append("DateMapper cannot map class: ").append(jsonvalue.getClass().getName()).toString());
	// try
	// {
	// return Timestamp.valueOf(((JSONString) jsonvalue).getValue().trim());
	// } catch (IllegalArgumentException illegalargumentexception)
	// {
	// return null;
	// }
	// }
	//
	// private TimestampMapper()
	// {
	// }
	//
	// }

	public JsonRpcServlet()
	{
		logger = LogFactory.getLog(JsonRpcServlet.class);
		methodCache = new HashMap();
	}

	public void init(ServletConfig servletconfig) throws ServletException
	{
		super.init(servletconfig);
		// JSONMapper.addHelper(new DateMapper());
		// JSONMapper.addHelper(new TimeMapper());
		// JSONMapper.addHelper(new TimestampMapper());
	}

	protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException
	{
		doPost(httpservletrequest, httpservletresponse);
	}

	protected void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws ServletException, IOException
	{
		String s = httpservletrequest.getPathInfo() != null ? httpservletrequest.getPathInfo() : "";
		String as[] = s.split("\\/");
		call(as[1], as[2], httpservletrequest, httpservletresponse);
	}

	protected void call(String className, String mothodName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Object obj = SpringBeanLoader.getBean(className);// 如果是spring、从ProxyFactory中取
		// 反射调用方法并将方法的结果值返回输出成json，不允许出现重名的方法
		if (obj == null)
		{
			response.sendError(404, (new StringBuilder()).append("服务不存在:").append(request.getPathInfo()).toString());
			return;
		}
		// 执行方法
		String methodStr = (new StringBuilder()).append(className).append(".").append(mothodName).toString();
		List obj1 = (List) methodCache.get(methodStr);
		if (obj1 == null)
		{
			ArrayList arraylist = new ArrayList();
			for (Class class1 = obj.getClass(); class1 != null; class1 = class1.getSuperclass())
				arraylist.addAll(Arrays.asList(class1.getInterfaces()));

			obj1 = new ArrayList();
			for (Iterator iterator = arraylist.iterator(); iterator.hasNext();)
			{
				Class class2 = (Class) iterator.next();
				Method amethod[] = class2.getMethods();
				Method amethod1[] = amethod;
				int j = amethod1.length;
				int k = 0;
				while (k < j)
				{
					Method method = amethod1[k];
					if (method.getName().equals(mothodName))
						obj1.add(method);
					k++;
				}
			}

			methodCache.put(methodStr, obj1);
		}
		try
		{
			if (obj1.size() == 0)
			{
				response.sendError(404, (new StringBuilder()).append("方法不存在:").append(request.getPathInfo()).toString());
				return;
			}
		} catch (Exception exception)
		{
			throw new ServletException(exception);
		}
		if (obj1.size() == 1)
		{
			Class[] aclass = OgnlRuntime.getParameterTypes((Method) (obj1.get(0)));
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter printwriter = response.getWriter();
			// 参数、判断服务的方法参数类型，如果是map，则转化为Map<String,
			// String>如果是其他类型"com.kingtake."开头，利用BeanUtils赋值
			Map<String, String[]> map = request.getParameterMap();
			Object para = null;
			List paras = new ArrayList();
			for (int i = 0; i < aclass.length; i++)
			{
				if (aclass[i].isAssignableFrom(HttpServletRequest.class))
				{
					paras.add(request);
					continue;
				}
				if (aclass[i].isAssignableFrom(HttpServletResponse.class))
				{
					paras.add(response);
					continue;
				}
				// 如果是map
				if (aclass[i].isAssignableFrom(Map.class))
				{
					para = new HashMap();
					for (String cName : map.keySet())
					{
						((Map) para).put(cName, map.get(cName)[0]);
					}
				}
				// 如果是实体bean
				if (aclass[i].getName().matches("[\\w.]*bean[.\\w]+"))
				{
					try
					{
						para = aclass[i].newInstance();
						for (String cName : map.keySet())
						{
							try
							{
								BeanUtils.setProperty(para, cName.toLowerCase(), map.get(cName)[0]);
							} catch (IllegalAccessException e)
							{
								e.printStackTrace();
							} catch (InvocationTargetException e)
							{
								e.printStackTrace();
							}
						}
					} catch (InstantiationException e1)
					{
						e1.printStackTrace();
					} catch (IllegalAccessException e1)
					{
						e1.printStackTrace();
					}
				}

				paras.add(para);
			}

			try
			{
				Object obj4;
				if (map == null)
					obj4 = ((Method) (obj1.get(0))).invoke(obj);
				else
					obj4 = ((Method) (obj1.get(0))).invoke(obj, paras.toArray());
				String rtn = "{\"success\":true";
				if (obj4 == null)
				{
					rtn += ",\"data\":null";
				} else
				{
					rtn += ",\"data\":";
					if (obj4 instanceof String)
						rtn += obj4;
					if (obj4 instanceof List)
						rtn += JSONArray.fromObject(obj4).toString();
					if (obj4 instanceof Map)
						rtn += JSONObject.fromObject(obj4).toString();
					if (obj4 instanceof Page)
						rtn += JSONObject.fromObject(obj4).toString();
				}
				rtn += "}";
				getJson(request, response, rtn);
			} catch (Exception e)
			{
				Throwable throwable = e.getCause();
				if ((throwable instanceof SystemException) || !throwable.getClass().getName().startsWith("com.vanceinfo."))
				{
					request.setAttribute("errorInfo", getMessage(throwable));
					response.sendError(500, "系统内部错误");
					logger.error("系统内部错误", throwable);
				} else
				{
					String s4 = getMessage(throwable);
					printwriter.print(s4);
				}
			}
		}
	}

	private static void getJson(HttpServletRequest request, HttpServletResponse response, String rtn) throws IOException
	{
		String callback = request.getParameter("callback");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (StringUtils.hasLength(callback))
		{
			out.write(callback + "(" + rtn + ")");
		} else
		{
			out.write(rtn);
		}
		response.flushBuffer();
	}

	private String getMessage(Throwable throwable)
	{
		StringBuilder stringbuilder = new StringBuilder();
		Throwable throwable1 = throwable;
		if (throwable instanceof DaoException)
		{
			Throwable throwable2 = throwable.getCause().getCause();
			if (throwable2 instanceof SQLException)
			{
				stringbuilder.append(throwable2.getMessage());
				return stringbuilder.toString();
			}
		} else
		{
			stringbuilder.append(throwable1.getMessage());
		}
		return stringbuilder.toString();
	}

	public static void main(String args[]) throws ServletException, IOException
	{
	}

}
