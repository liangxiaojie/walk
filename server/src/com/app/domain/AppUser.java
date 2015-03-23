package com.app.domain;

import java.util.Date;

public class AppUser
{
	private String id;

	private String userId;

	private String userName;

	private String userPass;

	private Date createTime;

	private String phoneNumber;

	private String statu;

	private String flag;

	private Date lastLogtime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserPass()
	{
		return userPass;
	}

	public void setUserPass(String userPass)
	{
		this.userPass = userPass;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getStatu()
	{
		return statu;
	}

	public void setStatu(String statu)
	{
		this.statu = statu;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public Date getLastLogtime()
	{
		return lastLogtime;
	}

	public void setLastLogtime(Date lastLogtime)
	{
		this.lastLogtime = lastLogtime;
	}
}