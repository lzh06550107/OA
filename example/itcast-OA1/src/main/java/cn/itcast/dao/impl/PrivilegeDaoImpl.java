package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IPrivilegeDao;
import cn.itcast.domain.Privilege;
@Repository
public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements
		IPrivilegeDao {

	/**
	 * 查找顶级权限
	 */
	public List<Privilege> findTopList() {
		String hql = "FROM Privilege p WHERE p.parent IS NULL ";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public List<String> findAllUrl() {
		String hql = "SELECT url FROM Privilege WHERE url IS NOT NULL";
		return this.getSession().createQuery(hql).list();
	}

}
