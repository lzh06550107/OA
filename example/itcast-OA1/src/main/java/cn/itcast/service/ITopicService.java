package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Topic;
import cn.itcast.utils.HQLHelper;

public interface ITopicService {

	public List<Topic> findTopicByForum(Forum model);

	public void save(Topic model);

	public Topic getById(Long id);

	public PageBean getPageBean(HQLHelper hh, int currentPage);

}
