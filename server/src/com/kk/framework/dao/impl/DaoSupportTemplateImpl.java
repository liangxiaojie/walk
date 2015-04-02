package com.kk.framework.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.kk.framework.dao.DaoSupportTemplate;
import com.kk.framework.dao.Page;

public class DaoSupportTemplateImpl extends SqlSessionDaoSupport implements DaoSupportTemplate
{

	public DaoSupportTemplateImpl()
	{
	}

	/**
	 * 单独获取连接 注意：必须手动关闭
	 */
	public Connection getConnection() throws SQLException
	{
		Connection conn = getSqlSession().getConnection();
		conn.setAutoCommit(false);
		return conn;
	}

	public int delete(String s, Object obj)
	{
		return getSqlSession().delete(s, obj);
	}

	public int delete(String s)
	{
		return getSqlSession().delete(s);
	}

	public Object get(String s, Object obj)
	{
		return getSqlSession().selectOne(s, obj);
	}

	public Object get(String s)
	{
		return getSqlSession().selectOne(s);
	}

	public Object getValue(String s)
	{
		Map map = (Map) getSqlSession().selectOne(s);
		String s1 = (String) map.keySet().iterator().next();
		return map.get(s1);
	}

	public Object getValue(String s, Object obj)
	{
		Map map = (Map) getSqlSession().selectOne(s, obj);
		String s1 = (String) map.keySet().iterator().next();
		return map.get(s1);
	}

	public int insert(String s, Object obj)
	{
		return getSqlSession().insert(s, obj);
	}

	public int insert(String s)
	{
		return getSqlSession().insert(s);
	}

	public List query4list(String s, Object obj)
	{
		return getSqlSession().selectList(s, obj);
	}

	public List query4list(String s)
	{
		return getSqlSession().selectList(s);
	}

	public int update(String s, Object obj)
	{
		return getSqlSession().update(s, obj);
	}

	public int update(String s)
	{
		return getSqlSession().update(s);
	}

	public Page query4Page(String s, String s1, Object obj, int i, int j)
	{
		int k = Integer.valueOf(String.valueOf(getValue(s1, obj))).intValue();
		RowBounds rowbounds = new RowBounds(i * j, j);
		List list = getSqlSession().selectList(s, obj, rowbounds);
		return new Page(i, j, list, k);
	}
}
