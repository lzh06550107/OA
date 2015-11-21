package cn.itcast.dao;

import java.util.List;

import cn.itcast.base.IBaseDao;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;

public interface IReplyDao extends IBaseDao<Reply> {

	public List<Reply> getReplyByTopic(Topic model);

	public PageBean getPageBean(int currentPage, Topic model);

}
