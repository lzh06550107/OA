package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Privilege;

public interface IPrivilegeService {

	List<Privilege> findAll();

	List<Privilege> getByIds(Long[] privilegeIds);

	List<Privilege> findTopList();

	List<String> findAllUrl();

}
