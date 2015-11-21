package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IPrivilegeDao;
import cn.itcast.domain.Privilege;
import cn.itcast.service.IPrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl implements IPrivilegeService {
	@Resource
	private IPrivilegeDao privilegeDao;

	public List<Privilege> findAll() {
		return privilegeDao.findAll();
	}

	public List<Privilege> getByIds(Long[] privilegeIds) {
		return privilegeDao.getByIds(privilegeIds);
	}

	@Override
	public List<Privilege> findTopList() {
		return privilegeDao.findTopList();
	}

	@Override
	public List<String> findAllUrl() {
		return privilegeDao.findAllUrl();
	}
}
