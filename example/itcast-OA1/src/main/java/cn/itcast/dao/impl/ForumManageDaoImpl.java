package cn.itcast.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.IForumManageDao;
import cn.itcast.domain.Forum;

@Repository
public class ForumManageDaoImpl extends BaseDaoImpl<Forum> implements
		IForumManageDao {

	/**
	 * 保持对象时同时把对象的position设置为id的值
	 */
	@Override
	public void save(Forum entity) {
		super.save(entity); // 先保存entity，但position为空值，保存后entity对象持久化
		// 修改持久化对象的值导致被刷新
		entity.setPosition(entity.getId().intValue());
	}

	/**
	 * 根据升序来排列查询的结果
	 */
	@Override
	public List<Forum> findAll() {
		String hql = "FROM Forum p ORDER BY p.position";
		return this.getSession().createQuery(hql).list();
	}

	/**
	 * 查找当前按升序排列记录的前一个记录，注意若当前记录是第一个，则返回NULL，存在空指针异常，可在页面层次控制第一条记录上移
	 */
	@Override
	public void moveUp(Long id) {
		// 找到需要移动的对象
		Forum currentForum = this.getById(id);
		// 根据对象的position的值查询出升序排列后它前面的一行对象
		int temp = currentForum.getPosition();
		String hql = "FROM Forum p WHERE p.position < ? ORDER BY p.position DESC";
		Forum priviousForum = (Forum) this.getSession().createQuery(hql)
				.setParameter(0, temp).setFirstResult(0).setMaxResults(1)
				.uniqueResult();
		// 交换两个对象position,然后更新
		currentForum.setPosition(priviousForum.getPosition());
		priviousForum.setPosition(temp);
		//持久化对象自动保存更新
	}
	/**
	 * 查找当前按升序排列记录的后一个记录，注意若当前记录是最后一个，则返回NULL，存在空指针异常，可在页面层次控制最后一条记录下移
	 */
	public void moveDown(Long id) {
		// 找到需要移动的对象
		Forum currentForum = this.getById(id);
		// 根据对象的position的值查询出升序排列后它前面的一行对象
		int temp = currentForum.getPosition();
		String hql = "FROM Forum p WHERE p.position > ? ORDER BY p.position ASC";
		Forum priviousForum = (Forum) this.getSession().createQuery(hql)
				.setParameter(0, temp).setFirstResult(0).setMaxResults(1)
				.uniqueResult();
		// 交换两个对象position,然后更新
		currentForum.setPosition(priviousForum.getPosition());
		priviousForum.setPosition(temp);
		//持久化对象自动保存更新
	}

}
