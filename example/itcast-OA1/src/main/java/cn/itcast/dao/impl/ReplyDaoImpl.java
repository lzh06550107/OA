package cn.itcast.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IReplyDao;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;
@Repository
public class ReplyDaoImpl extends BaseDaoImpl<Reply> implements IReplyDao {
	
	/**
	 * 没分页查询
	 */
	public List<Reply> getReplyByTopic(Topic model) {
		String hql = "FROM Reply r WHERE r.topic = ? ORDER BY r.postTime ASC";
		return this.getSession().createQuery(hql).setParameter(0, model).list();
	}

	/**
	 * 分页查询
	 */
	public PageBean getPageBean(int currentPage, Topic model) {
		int pageSize = 10; //假设每页10条记录
		int firstResult = (currentPage-1) * pageSize;
		String hql = "FROM Reply r WHERE r.topic = ? ORDER BY r.postTime ASC";
		//获取记录
		List recordList = this.getSession().createQuery(hql)
				.setParameter(0, model)
				.setFirstResult(firstResult)
				.setMaxResults(pageSize).list();
		hql = "SELECT COUNT(id) FROM Reply r WHERE r.topic= ? ";
		//获取记录总数
		Long recordCount = (Long) this.getSession().createQuery(hql).setParameter(0, model).uniqueResult();
		return new PageBean(currentPage, pageSize, recordCount.intValue(), recordList);
	}
}
