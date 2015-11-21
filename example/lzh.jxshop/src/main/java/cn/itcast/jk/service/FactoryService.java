package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.jk.domain.Factory;

public interface FactoryService {
	public List<Factory> find(Factory entity);
	public Factory get(Serializable id);
	public void insert(Factory entity);
	public void update(Factory entity);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
	public void changeState(Map<String,Object> map);
	
	public void print(Factory factory, HttpServletRequest request, HttpServletResponse response);
	public List<Factory> combo();
}
