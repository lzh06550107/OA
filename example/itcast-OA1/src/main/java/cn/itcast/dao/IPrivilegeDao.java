package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.Privilege;

public interface IPrivilegeDao extends IBaseDao<Privilege> {

	List<Privilege> findTopList();

	List<String> findAllUrl();

}
