package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IDepartmentDao;
import cn.itcast.domain.Department;
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements IDepartmentDao {

	/**
	 * 查询所有的顶级部门
	 */
	public List<Department> findTopList() {
		String hql = "FROM Department d WHERE d.parent IS NULL";
		return this.getSession().createQuery(hql).list();
	}
	/**
	 * 查询某个部门的所有子部门
	 */
	public List<Department> findChildren(Long parentId) {
		String hql = "FROM Department d WHERE d.parent.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, parentId).list();
	}

}
