package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IForumDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.service.IForumService;
import cn.itcast.utils.HQLHelper;
@Service
@Transactional
public class ForumServiceImpl implements IForumService {
	@Resource
	private IForumDao forumDao;

	@Override
	public List<Forum> findAll() {
		List<Forum> list = forumDao.findAll();
		return list;
	}

	@Override
	public Forum getById(Long id) {
		return forumDao.getById(id);
	}

	@Override
	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return forumDao.getPageBean(hh, currentPage);
	}
	
}
