package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.jk.domain.ContractProduct;

public interface ContractProductService {
	public List<ContractProduct> find(ContractProduct entity);
	public ContractProduct get(Serializable id);
	public void insert(ContractProduct entity);
	public void update(ContractProduct entity);
	public void delete(Serializable id);
	public void delete(Serializable[] ids);
	
}
