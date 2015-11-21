package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IReplyDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;
import cn.itcast.service.IReplyService;
import cn.itcast.utils.HQLHelper;
@Service
@Transactional
public class ReplyServiceImpl implements IReplyService {
	@Resource
	private IReplyDao replyDao;

	/**
	 * 发表回复
	 */
	public void save(Reply model) {
		replyDao.save(model);
		
		Forum forum = model.getTopic().getForum(); //持久化对象
		forum.setArticleCount(forum.getArticleCount()+1);
		
		Topic topic = model.getTopic(); //持久化对象
		topic.setLastUpdateTime(model.getPostTime()); //回复当前主题的最后更新时间
		topic.setLastReply(model); //主题的最后一个回复
		topic.setReplyCount(topic.getReplyCount()+1); //回复数量加1
		//持久化自动更新

	}

	@Override
	public List<Reply> getReplyByTopic(Topic model) {
		return replyDao.getReplyByTopic(model);
	}
	//作废，用下面的代替
	@Override
	public PageBean getPageBean(int currentPage, Topic model) {
		return replyDao.getPageBean(currentPage, model);
	}

	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return replyDao.getPageBean(hh, currentPage);
	}
}
