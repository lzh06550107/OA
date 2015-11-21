package cn.itcast.jk.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.vo.OutProduct;


public interface ContractDao extends BaseDao<Contract> {
	public cn.itcast.jk.domain.vo.Contract view(Serializable id);
	public void changeState(Map<String,Object> map);
	public List<OutProduct> findOutProduct(Serializable inputDate);
	public List<String> getExtName(Serializable contractProductId);
}
