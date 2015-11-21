package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.ITopicDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Topic;
import cn.itcast.service.ITopicService;
import cn.itcast.utils.HQLHelper;
@Transactional
@Service
public class TopicServiceImpl implements ITopicService {
	@Resource
	private ITopicDao topicDao;

	@Override
	public List<Topic> findTopicByForum(Forum model) {
		return topicDao.findTopicByForum(model);
	}
	
	public void save(Topic model) {
		topicDao.save(model);
		
		Forum forum = model.getForum(); //forum为持久化对象
		forum.setTopicCount(forum.getTopicCount()+1); //当前主题所在的版块的主题数增1
		forum.setArticleCount(forum.getArticleCount()+1); //当前主题所在的版块的文章数增1
		forum.setLastTopic(model); //设置版块的最后发表的主题为当前主题
		//持久化对象自动更新
		
	}


	public Topic getById(Long id) {
		return topicDao.getById(id);
	}

	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return topicDao.getPageBean(hh, currentPage);
	}
}
