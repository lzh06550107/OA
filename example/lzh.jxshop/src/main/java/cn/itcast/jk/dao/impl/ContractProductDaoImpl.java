package cn.itcast.jk.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.domain.ContractProduct;
@Repository
public class ContractProductDaoImpl extends BaseDaoImpl<ContractProduct>
		implements ContractProductDao {
	ContractProductDaoImpl(){
		this.setNs("cn.itcast.jk.mapper.ContractProductMapper.");
	}

	public void deleteByContractId(Serializable id) {
		this.getSqlSession().delete(this.getNs()+"deleteByContractId", id);
	}

}
