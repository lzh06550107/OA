package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IForumDao;
import cn.itcast.domain.Forum;
@Repository
public class ForumDaoImpl extends BaseDaoImpl<Forum> implements IForumDao {

	@Override
	public List<Forum> findAll() {
		String hql = "FROM Forum f ORDER By f.position";
		return this.getSession().createQuery(hql).list();
	}
	
}
