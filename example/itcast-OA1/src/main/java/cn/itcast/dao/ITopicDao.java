package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.Topic;

public interface ITopicDao extends IBaseDao<Topic> {

	List<Topic> findTopicByForum(Forum model);

}
