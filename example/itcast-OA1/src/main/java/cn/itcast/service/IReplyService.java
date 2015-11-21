package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;
import cn.itcast.utils.HQLHelper;

public interface IReplyService {

	public void save(Reply model);

	public List<Reply> getReplyByTopic(Topic model);

	public PageBean getPageBean(int currentPage, Topic model);

	public PageBean getPageBean(HQLHelper hh, int currentPage);

}
