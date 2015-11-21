package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IDepartmentDao;
import cn.itcast.domain.Department;
import cn.itcast.service.IDepartmentService;
@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {
	
	@Resource
	private IDepartmentDao departmentDao;
	
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	public void delete(Department model) {
		departmentDao.delete(model.getId());		
	}

	public Department getById(Long parentId) {
		return departmentDao.getById(parentId);
	}

	@Override
	public void save(Department model) {
		departmentDao.save(model);
		
	}

	public void update(Department model) {
		departmentDao.update(model);
		
	}

	@Override
	public List<Department> findTopList() {
		return departmentDao.findTopList();
	}

	@Override
	public List<Department> findChildren(Long parentId) {
		return departmentDao.findChildren(parentId);
	}
}
