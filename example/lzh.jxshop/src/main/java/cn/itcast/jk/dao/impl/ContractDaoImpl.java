package cn.itcast.jk.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.vo.OutProduct;
@Repository
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements
		ContractDao {
	ContractDaoImpl(){
		this.setNs("cn.itcast.jk.mapper.ContractMapper."); //注意别忘了最后的小点
	}

	public cn.itcast.jk.domain.vo.Contract view(Serializable id) {
		return this.getSqlSession().selectOne(this.getNs()+"view", id);
	}

	public void changeState(Map<String,Object> map){
		this.getSqlSession().update(this.getNs() + "changeState", map);
	}

	public List<OutProduct> findOutProduct(Serializable inputDate) {
		return this.getSqlSession().selectList(this.getNs() + "findOutProduct", inputDate+"%");
	}

	public List<String> getExtName(Serializable contractProductId) {
		return this.getSqlSession().selectList(this.getNs() + "getExtName", contractProductId);
	}
}
