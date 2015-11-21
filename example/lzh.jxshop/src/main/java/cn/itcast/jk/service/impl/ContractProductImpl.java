package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.service.ContractProductService;
@Service
@Transactional
public class ContractProductImpl implements ContractProductService {
	@Resource
	private ContractProductDao contractProductDao;

	public List<ContractProduct> find(ContractProduct entity) {
		return contractProductDao.find(entity);
	}

	public ContractProduct get(Serializable id) {
		return contractProductDao.get(id);
	}

	public void insert(ContractProduct entity) {
		contractProductDao.insert(entity);
	}

	public void update(ContractProduct entity) {
		contractProductDao.update(entity);
	}

	public void delete(Serializable id) {
		contractProductDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		contractProductDao.delete(ids);
	}


}
