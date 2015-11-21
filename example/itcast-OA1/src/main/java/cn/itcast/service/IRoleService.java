package cn.itcast.service;


import java.util.List;

import cn.itcast.domain.Role;

public interface IRoleService {

	public List<Role> finaAll();

	public void delete(Role model);

	public Role getById(Long id);

	public void update(Role model);

	public void save(Role model);

	public List<Role> getByIds(Long[] roleIds);
	
}
