package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.ITopicDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.Topic;
@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements ITopicDao {

	/**
	 * 根据板块查询主题列表
	 * select * from itcast_topic order by (case type when 2 then 2 else 1 end) desc, postTime desc;
	 */
	public List<Topic> findTopicByForum(Forum model) {
		//String hql = "FROM Topic t WHERE t.forum = ?";
		String hql = "FROM Topic t WHERE t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 1 END) DESC, t.postTime DESC";
		
		return this.getSession().createQuery(hql).setParameter(0, model).list();
	}

}
