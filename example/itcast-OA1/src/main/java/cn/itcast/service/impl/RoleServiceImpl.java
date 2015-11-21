package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IRoleDao;
import cn.itcast.domain.Role;
import cn.itcast.service.IRoleService;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	
	@Resource
	private IRoleDao roleDao;
	
	public List<Role> finaAll() {
		return roleDao.findAll();
	}

	public void delete(Role model) {
		roleDao.delete(model.getId());		
	}

	public Role getById(Long id) {
		return roleDao.getById(id);
	}

	public void update(Role model) {
		roleDao.update(model);
	}

	public void save(Role model) {
		roleDao.save(model);
	}

	@Override
	public List<Role> getByIds(Long[] roleIds) {
		return roleDao.getByIds(roleIds);
	}
	 
}
