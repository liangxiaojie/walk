package com.app.common;

import java.io.File;

public class Notes
{
	public final static String APPUSER_LOGIN_SESSION = "APPUSER_LOGIN_SESSION"; // 用户后存放session名

	public final static String AppUserFlagActive = "0"; // 用户未删除
	public final static String AppUserFlagDeleted = "1"; // 用户已删除

	public final static String AppUserStatuEnable = "0"; // 用户启用
	public final static String AppUserStatuDisable = "1"; // 用户禁用

	public final static String AppPathStatuActive = "0"; // 路径评价未删除
	public final static String AppPathStatuDeleted = "1"; // 路径评价已删除

	public final static String AppEvaluationStatuActive = "0"; // 设施评价未删除
	public final static String AppEvaluationStatuDeleted = "1"; // 设施评价已删除

	public final static String AppInfoStatuActive = "0"; // 设施信息未删除
	public final static String AppInfoStatuDeleted = "1"; // 设施信息已删除

	public static String getRootPath()
	{
		String classPath = Notes.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator))
		{
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator))
		{
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

}
