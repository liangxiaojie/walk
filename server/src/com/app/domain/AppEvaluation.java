package com.app.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppEvaluation
{
	private String id;

	private String address;

	private String evalType;

	private String evalContent;

	private String userId;

	private Date uploadtime;

	private String longitude;

	private String latitude;

	private String statu;

	private List picList;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getEvalType()
	{
		return evalType;
	}

	public void setEvalType(String evalType)
	{
		this.evalType = evalType;
	}

	public String getEvalContent()
	{
		return evalContent;
	}

	public void setEvalContent(String evalContent)
	{
		this.evalContent = evalContent;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUploadtime()
	{
		String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(uploadtime);
		return dateStr;
	}

	public void setUploadtime(Date uploadtime)
	{
		this.uploadtime = uploadtime;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getStatu()
	{
		return statu;
	}

	public void setStatu(String statu)
	{
		this.statu = statu;
	}

	public List getPicList()
	{
		return picList;
	}

	public void setPicList(List picList)
	{
		this.picList = picList;
	}

}