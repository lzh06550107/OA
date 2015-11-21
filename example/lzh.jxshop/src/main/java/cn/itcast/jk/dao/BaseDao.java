package cn.itcast.jk.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 泛型类，基础的DAO接口
 * @author:LZH
 * @time:2015年7月8日下午4:29:13
 */
public interface BaseDao<T> {
	public List<T> find(T entity);
	public T get(Serializable id);
	public void insert(T entity);
	public void update(T entity);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
}
