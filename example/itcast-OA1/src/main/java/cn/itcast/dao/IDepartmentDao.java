package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.Department;

public interface IDepartmentDao extends IBaseDao<Department> {

	List<Department> findTopList();

	List<Department> findChildren(Long parentId);

}
