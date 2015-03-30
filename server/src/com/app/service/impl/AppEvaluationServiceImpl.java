package com.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.app.common.Notes;
import com.app.common.PicCompressUtil;
import com.app.domain.AppUser;
import com.app.service.AppEvaluationService;
import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.dao.Page;
import com.kk.framework.service.BaseService;

public class AppEvaluationServiceImpl extends BaseService implements AppEvaluationService
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

	public Page searchAppEvaluation(Map<String, String> parasMap, HttpServletRequest request)
	{
		List rtnList = new ArrayList();
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		AppUser appUser = (AppUser) request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("userId", appUser.getUserId());
		paraMap.put("statu", Notes.AppEvaluationStatuActive); // 0未删除 1已删除

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("AppEvaluation.Mapper.searchAppEvaluation", "AppEvaluation.Mapper.searchAppEvaluationCount", paraMap,
				pageNo, pageSize);

		return page;
	}

	public Map<String, String> queryAppEvaluation(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("id", parasMap.get("id"));

		Map<String, String> rtnMap = (Map<String, String>) getDaoSupportTemplate().get("AppEvaluation.Mapper.queryAppEvaluation", paraMap);

		return rtnMap;
	}

	public Map<String, String> saveAppEvaluation(Map<String, String> parasMap, HttpServletRequest request)
	{
		Map<String, String> rtnMap = new HashMap<String, String>();

		if (parasMap.get("action") != null && parasMap.get("action").equals("query") && parasMap.get("server") != null && parasMap.get("server").equals("uploader"))
		{

		} else
		{
			//先插评价表主表，然后将主表主键给图片表做外键。
			Map<String, String> paraMap = new HashMap<String, String>();
			String id = java.util.UUID.randomUUID().toString().replaceAll("-", "");
			paraMap.put("id", id);
			
			String address = "";
			String eval_content = "";
			try
			{
				address = java.net.URLDecoder.decode(parasMap.get("address"), "utf-8");
				eval_content = java.net.URLDecoder.decode(parasMap.get("eval_content"), "utf-8");
			} catch (UnsupportedEncodingException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			paraMap.put("address", address);
			paraMap.put("eval_content", eval_content);

			paraMap.put("eval_type", parasMap.get("eval_type"));

			AppUser appUser = (AppUser)request.getSession().getAttribute(Notes.APPUSER_LOGIN_SESSION);
			paraMap.put("user_id", appUser.getUserId());

			paraMap.put("longitude", parasMap.get("longitude"));
			paraMap.put("latitude", parasMap.get("latitude"));
			paraMap.put("statu", Notes.AppEvaluationStatuActive);
			
			daoSupportTemplate.update("AppEvaluation.Mapper.saveAppEvaluation", paraMap);
			
			//上传附件
			String projectPath = Notes.getRootPath();
			// 临时目录名
			String tempPath = projectPath+"\\file\\temp\\";
			// 真实目录名
			String filePath = projectPath+"\\file\\2eval\\";

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 最大缓存
			factory.setSizeThreshold(10 * 1024);
			// 设置临时文件目录
			factory.setRepository(new File(tempPath));
			ServletFileUpload upload = new ServletFileUpload(factory);

			try
			{
				// 获取所有文件列表
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items)
				{
					if (!item.isFormField())
					{
						// 文件名
						String fileName = item.getName();
						// 检查文件后缀格式
						String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						// 创建文件唯一名称
						String uuid = UUID.randomUUID().toString();
						// 真实上传路径
						StringBuffer sbRealPath = new StringBuffer();
						sbRealPath.append(filePath).append(uuid).append(".").append(fileEnd);
						// 写入文件
						File file = new File(sbRealPath.toString());
						item.write(file);




						PicCompressUtil mypic = new PicCompressUtil();
						mypic.compressPic(filePath, filePath+"thumb\\", uuid+"."+fileEnd, uuid+"."+fileEnd, 100, 100, true);
						
						
						
						paraMap = new HashMap<String, String>();
						String picid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
						paraMap.put("id", picid);
						paraMap.put("fid", id);
						paraMap.put("filename", fileName);
						paraMap.put("filetype", fileEnd);
						paraMap.put("filedpath", "\\file\\2eval\\thumb\\" + uuid + "." + fileEnd );
						paraMap.put("filepath", "\\file\\2eval\\" + uuid + "." + fileEnd );
						paraMap.put("filesize", String.valueOf(item.getSize()));
						paraMap.put("walktype", "2");
						
						daoSupportTemplate.update("AppPicture.Mapper.saveAppPicture", paraMap);
					}// end of if
				}// end of for

				rtnMap.put("code", "S001");
				rtnMap.put("msg", "提交成功！");
			} catch (Exception e)
			{
				// 提示错误:比如文件大小
				e.printStackTrace();
				rtnMap.put("code", "E001");
				rtnMap.put("msg", "提交失败，错误原因:"+e.getMessage()+"！");
			}
		}

		return rtnMap;
	}

	@Override
	public Page getAppEvaluations(Map<String, String> parasMap, HttpServletRequest request) {

		Map<String, String> paraMap = new HashMap<String, String>();

		int pageNo = Integer.parseInt(parasMap.get("pageNo"));
		int pageSize = Integer.parseInt(parasMap.get("pageSize"));

		Page page = getDaoSupportTemplate().query4Page("AppEvaluation.Mapper.getAppEvaluations", "AppEvaluation.Mapper.getAppEvaluationsCount", paraMap, pageNo, pageSize);

		return page;
	}
}