package cn.itcast.dao;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.Forum;

public interface IForumManageDao extends IBaseDao<Forum> {

	void moveUp(Long id);
	
	void moveDown(Long id);

}
