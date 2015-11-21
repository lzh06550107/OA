package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.vo.OutProduct;


public interface ContractService {
	public List<Contract> find(Contract entity);
	public Contract get(Serializable id);
	public void insert(Contract entity);
	public void update(Contract entity);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
	
	public cn.itcast.jk.domain.vo.Contract view(Serializable id);
	public void changeState(Map<String,Object> map);
	
	public void outProductPrint(String inputDate, HttpServletResponse response,  String userAgent)throws Exception;
	public void print(String contractId, HttpServletRequest request,
			HttpServletResponse response);
	
}
